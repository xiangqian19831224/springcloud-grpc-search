package com.ec.search;

import com.ec.search.eureka.EurekaNameResolverProvider;
import com.netflix.discovery.EurekaClientConfig;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.util.RoundRobinLoadBalancerFactory;
import org.ec.search.grpc.services.Request;
import org.ec.search.grpc.services.Response;
import org.ec.search.grpc.services.SearchServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * TODO: comment here
 */
@Service
public class SearchServiceClient {
	private Logger logger = LoggerFactory.getLogger(SearchServiceClient.class);

	@Autowired
	EurekaClientConfig eurekaClientConfig;

	private SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;

	public String search(String query, int pageNum, int pageSize, int type){
		Request.Builder builder = Request.newBuilder();
		builder.setQuery(query);
		builder.setPageNum(pageNum);
		builder.setPageSize(pageSize);
		Request request = builder.build();

		Response response = searchServiceBlockingStub.searchByQuery(request);
		String docs = response.getDocs();
		return docs;
	}

	@PostConstruct
	private void initializeClient() {

		ManagedChannel channel = ManagedChannelBuilder
				.forTarget("eureka://grpc-server")
				.nameResolverFactory(new EurekaNameResolverProvider(eurekaClientConfig, "grpc.port"))
				.loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance())
				.usePlaintext(true)
				.build();

		searchServiceBlockingStub = SearchServiceGrpc.newBlockingStub(channel);
	}
}
