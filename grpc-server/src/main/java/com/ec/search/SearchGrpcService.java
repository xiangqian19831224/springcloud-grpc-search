package com.ec.search;

import io.grpc.stub.StreamObserver;
import org.ec.search.grpc.services.Request;
import org.ec.search.grpc.services.Response;
import org.ec.search.grpc.services.SearchServiceGrpc;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: comment here
 */
@GRpcService
public class SearchGrpcService extends SearchServiceGrpc.SearchServiceImplBase {
	private Logger logger = LoggerFactory.getLogger(SearchGrpcService.class);

	@Override
	public  void searchByQuery(Request request, StreamObserver<Response> responseObserver){
		logger.debug("Request: " + request);
		Response.Builder builder = Response.newBuilder();
		builder.setQuery(request.getQuery());
		builder.setDocs("[服务成功]");
		Response response = builder.build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
