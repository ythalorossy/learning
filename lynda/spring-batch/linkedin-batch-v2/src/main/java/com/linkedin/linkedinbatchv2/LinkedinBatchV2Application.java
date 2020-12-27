package com.linkedin.linkedinbatchv2;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableBatchProcessing
public class LinkedinBatchV2Application {

	private static final int _CHUNK_SIZE = 10;

	private static final String DATA_SHIPPED_ORDERS_CSV_PATH = "data/shipped_orders.csv";
	private static final String DATA_SHIPPED_ORDERS_OUTPUT_CSV = "/home/yross/Documents/learning/lynda/spring-batch/data/shipped_orders_output.csv";
	private static final String DATA_SHIPPED_ORDERS_OUTPUT_JSON = "/home/yross/Documents/learning/lynda/spring-batch/data/shipped_orders_output.json";

	public static String[] tokens = new String[] { "order_id", "first_name", "last_name", "email", "cost", "item_id",
			"item_name", "ship_date" };

	public static String[] names = new String[] { "orderId", "firstName", "lastName", "email", "cost", "itemId",
			"itemName", "shipDate" };

	public static String ORDER_SQL = "select order_id, first_name, last_name, "
			+ "email, cost, item_id, item_name, ship_date " + "from SHIPPED_ORDER order by order_id";

	// public static String INSERT_ORDER_SQL = "insert into "
	// 		+ "SHIPPED_ORDER_OUTPUT(order_id, first_name, last_name, email, item_id, item_name, cost, ship_date)"
	// 		+ " values(:orderId, :firstName, :lastName, :email, :itemId, :itemName, :cost, :shipDate)";

	public static String INSERT_ORDER_SQL = "insert into "
			+ "TRACKED_ORDER(order_id, first_name, last_name, email, item_id, item_name, cost, ship_date, tracking_number, free_shipping) "
			+ " values(:orderId, :firstName, :lastName,:email, :itemId, :itemName, :cost, :shipDate, :trackingNumber, :freeShipping)";



	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Bean
	public ItemProcessor<TrackedOrder, TrackedOrder> freeShippingItemProcessor() {
		return new FreeShippingItemProcessor();
	}

	@Bean
	public ItemProcessor<Order, TrackedOrder> compositeItemProcessor() {
		return new CompositeItemProcessorBuilder<Order, TrackedOrder>()
			.delegates(
					orderValidatingItemProcessor(), 
					trackedOrderItemProcessor(), 
					freeShippingItemProcessor()
			)
			.build();
	}

	@Bean
	public  ItemProcessor<Order, TrackedOrder> trackedOrderItemProcessor() {
		return new TrackedOrderItemProcessor();
	}

	@Bean
	public ItemProcessor<Order, Order> orderValidatingItemProcessor() {
		BeanValidatingItemProcessor<Order> itemProcessor = new BeanValidatingItemProcessor<Order>();
		itemProcessor.setFilter(true);
		return itemProcessor;
	}

	@Bean
	public ItemWriter<TrackedOrder> itemWriterJsonFile() {
		return new JsonFileItemWriterBuilder<TrackedOrder>()
				.name("itemWriterJsonFile")
				.jsonObjectMarshaller(new JacksonJsonObjectMarshaller<TrackedOrder>())
				.resource(new FileSystemResource(new File(DATA_SHIPPED_ORDERS_OUTPUT_JSON))).build();
	}

	@Bean
	public ItemWriter<Order> itemWriterJdbcBatch() {
		return new JdbcBatchItemWriterBuilder<Order>()
				.dataSource(dataSource)
				.sql(INSERT_ORDER_SQL)
				// .itemPreparedStatementSetter(new OrderItemPreparedStatementSetter())
				.beanMapped().build();
	}

	@Bean
	public ItemWriter<Order> itemWriterFlatFile() {

		FlatFileItemWriter<Order> itemWriter = new FlatFileItemWriter<Order>();

		itemWriter.setResource(new FileSystemResource(new File(DATA_SHIPPED_ORDERS_OUTPUT_CSV)));

		DelimitedLineAggregator<Order> lineAggregator = new DelimitedLineAggregator<Order>();
		lineAggregator.setDelimiter(",");

		BeanWrapperFieldExtractor<Order> fieldExtractor = new BeanWrapperFieldExtractor<Order>();
		fieldExtractor.setNames(names);

		lineAggregator.setFieldExtractor(fieldExtractor);

		itemWriter.setLineAggregator(lineAggregator);

		return itemWriter;
	}

	@Bean
	public PagingQueryProvider queryProvider() throws Exception {

		SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();

		factory.setSelectClause("select order_id, first_name, last_name, email, cost, item_id, item_name, ship_date ");
		factory.setFromClause("SHIPPED_ORDER");
		factory.setSortKey("order_id");
		factory.setDataSource(dataSource);

		return factory.getObject();
	}

	/**
	 * JDBC Paging Item Reader - Thread safe
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public ItemReader<Order> itemReaderJdbcPaging() throws Exception {
		return new JdbcPagingItemReaderBuilder<Order>()
				.dataSource(dataSource)
				.name("jdbcPagingItemReader")
				.queryProvider(queryProvider())
				.rowMapper(new OrderRowMapper())
				.pageSize(_CHUNK_SIZE)
				.saveState(false)
				.build();
	}

	/**
	 * JDBC Item Reader
	 * 
	 * @return
	 */
	@Bean
	public ItemReader<Order> itemReaderJdbcCursor() {
		return new JdbcCursorItemReaderBuilder<Order>().dataSource(dataSource).name("jdbcCursorItemReader")
				.sql(ORDER_SQL).rowMapper(new OrderRowMapper()).build();
	}

	/**
	 * FlatFile Item Reader
	 * 
	 * @return
	 */
	@Bean
	public ItemReader<Order> itemReaderCSV() {

		FlatFileItemReader<Order> itemReader = new FlatFileItemReader<>();
		itemReader.setName("flatFileItemReader");
		itemReader.setLinesToSkip(1); // header on CSV file
		itemReader.setResource(new ClassPathResource(DATA_SHIPPED_ORDERS_CSV_PATH));

		DefaultLineMapper<Order> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(tokens);

		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new OrderFieldSetMapper());

		itemReader.setLineMapper(lineMapper);

		return itemReader;
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(10);
		return executor;
	}

	@Bean
	public Step chunkBasedStep() throws Exception {
		return this.stepBuilderFactory.get("chunkBasedStep")
				.<Order, TrackedOrder>chunk(_CHUNK_SIZE)
				.reader(itemReaderJdbcPaging())
				// .processor(orderValidatingItemProcessor())
				// .processor(trackedOrderItemProcessor())
				.faultTolerant()
				// .skip(OrderProcessingException.class)
				// .skipLimit(5)	// For the entire processing
				//.listener(new CustomSkipListener())
				.retry(OrderProcessingException.class)
				.retryLimit(3)	// For specifc item. Retries x times that specif item
				.listener(new CustomRetryListener())
				.processor(compositeItemProcessor())
				.writer(itemWriterJsonFile())
				.taskExecutor(taskExecutor())
				.build();
	}


	@Bean
	public Job job() throws Exception {
		return this.jobBuilderFactory.get("job")
			.start(chunkBasedStep())
			.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(LinkedinBatchV2Application.class, args);
	}

}
