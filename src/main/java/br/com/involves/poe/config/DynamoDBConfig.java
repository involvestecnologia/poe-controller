package br.com.involves.poe.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.apache.commons.lang.StringUtils;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@EnableDynamoDBRepositories(basePackages = "br.com.involves.poe")
@ComponentScan(basePackages = "br.com.involves.poe")
@PropertySource("classpath:application.properties")
public class DynamoDBConfig {

    @Value("${dynamodb.endpoint:}")
    private String amazonDynamoDBEndpoint;

    @Value("${aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${aws.secretkey}")
    private String amazonAWSSecretKey;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(
                amazonAWSCredentials());
        if (StringUtils.isNotEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        }
        return amazonDynamoDB;
    }

    //@Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
  //      return new MethodValidationPostProcessor();
    //}

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

}
