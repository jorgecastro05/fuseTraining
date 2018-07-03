package com.redhat.gpe.training.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;

public class SimpleCBRRoute extends RouteBuilder {

    @EndpointInject(ref="sourceDirectoryCsv")
    Endpoint sourceUri;

    @EndpointInject(ref="euroSinkCsv")
    Endpoint euroSinkUri;

    @EndpointInject(ref="usdSinkCsv")
    Endpoint usdSinkUri;

    @EndpointInject(ref="otherSinkCsv")
    Endpoint otherSinkUri;

    @Override
    public void configure() throws Exception {

        from(sourceUri)
                .log(">> processing CSV files: ${file:onlyname} <<")
                .choice()
                    .when()
                        .simple("${file:onlyname} == 'EUPayments.txt'")
                        .log("This is a Euro Payment: ${file:onlyname}")
                        .to(euroSinkUri)
                    .when()
                        .simple("${file:onlyname} == 'USPayments.txt'")
                        .log("This is a US Payment: ${file:onlyname}")
                        .to(usdSinkUri)
                    .otherwise()
                        .log("This is an other currency payment ${file:onlyname}")
                        .to(otherSinkUri);
    }

}
