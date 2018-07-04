package com.redhat.gpe.training.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnricherRoute extends RouteBuilder {

    private static SampleAggregator aggregationStrategy = new SampleAggregator();

	public void configure() throws Exception {
		from("timer:enrich?period=5s")
				.setBody(constant("message"))
                .log(">> Before enrichement my body is ${body}")
                .enrich("direct:resource",aggregationStrategy)
                .log(">> After enrichement. My body is ${body}");
		from("direct:resource")
                .setExchangePattern(ExchangePattern.InOut)
                .transform(constant("blah"));
	}

}
