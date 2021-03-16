package com.elastic;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

	private RestHighLevelClient client;

	@Bean
	public RestHighLevelClient prepareConnection() {
		RestClientBuilder restBuilder = RestClient.builder(new HttpHost("localhost", 9200));
		String username = "";// new String(environment.getProperty("zselastic.username").toString());
		String password = "";// new String(environment.getProperty("zselastic.password").toString());
		if (username != null & password != null) {
			final CredentialsProvider creadential = new BasicCredentialsProvider();
			creadential.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
			restBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
				@Override
				public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {

					return httpClientBuilder.setDefaultCredentialsProvider(creadential)
							.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(1).build());
				}
			});

			restBuilder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(10000) // time
																														// until
																														// a
																														// connection
																														// with
																														// the
																														// server
																														// is
																														// established.
					.setSocketTimeout(60000) // time of inactivity to wait for packets[data] to receive.
					.setConnectionRequestTimeout(0)); // time to fetch a connection from the connection pool 0 for
														// infinite.

			client = new RestHighLevelClient(restBuilder);
			return client;
		}
		return null;
	}

	/*
	 * it gets called when bean instance is getting removed from the context if
	 * scope is not a prototype
	 */
	/*
	 * If there is a method named shutdown or close then spring container will try
	 * to automatically configure them as callback methods when bean is being
	 * destroyed
	 */
	@PreDestroy
	public void clientClose() {
		try {
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
