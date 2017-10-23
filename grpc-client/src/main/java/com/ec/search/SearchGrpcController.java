package com.ec.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: comment here
 */
@RestController
public class SearchGrpcController {
	private Logger logger = LoggerFactory.getLogger(SearchGrpcController.class);

	@Autowired
	SearchServiceClient searchServiceGrpcClient;

	@RequestMapping(value="/search")
	public String search(String query, int pageNum, int pageSize, int sortType){
		logger.info("Query: " + query);
		String response = searchServiceGrpcClient.search(query, pageNum,pageSize,sortType);

		return response;
	}
}
