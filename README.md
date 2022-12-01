# Bandwidth Reporting API #

## Story ##

You work at an ISP which has a problem with customers consistently violating the ISP’s terms of service. Specifically, users are limited to an average of 1GB per month of data transfer, the total ingress plus the total egress, which is tracked on a rolling three-month average. The ISP’s business leaders have asked engineering to develop tools to help them track violators and provide users with information that will help them curb their usage.

## The Assignment ##

You will develop an HTTP based API and a client for that API that provides customers and the ISP’s business leaders with data about customer bandwidth usage. The API should return JSON formatted data. The client is left to the imagination of the developer but the business would prefer a web based UI.

While we would like all requirements implemented, this assignment should not consume more than 3 hours of your time. Please focus on building a production ready solution rather than a complete API (quality over speed).
​
This repository includes the scaffolding of a Spring Boot project with Spring MVC support. Fork the repository and submit your fork when you have completed your implementation. We ask that you submit your implementation within 10 days of receiving the instructions.

## Requirements ##

1. Provide a customer’s usage record using the customer id as the key. The response should include:

   * Customer id.
   * Each month’s total bandwidth usage.
   * The customer’s latest rolling three-month average.

2. Provide a bandwidth usage report, including all customers, which calculates the percentile into which the aggregate of each customer’s three-month rolling average over the previous two years falls. The report should include:

   * Customer id.
   * The percentile into which the customer falls.

3. Provide a bandwidth usage report, including all customers, that includes each customer’s rolling three-month average for each month of a user specified reporting period. The response should include:

    * Customer id.
    * The start of the reporting period.
    * The end of the reporting period.
    * The bandwidth usage for each of the three months included in the average of the month being reported.
    * The customer’s three-month average for the month being reported.

## Prerequisites ##

* Java 17+
* Gradle 7.5+​

## Guardrails and Guidance ##

* You may use any relational database system.

* H2 or another in-memory database is an acceptable solution for this exercise. We're interested in your schema design not whether you can set up an RDMS.

* You may use any third-party libraries you feel are appropriate.

* The API will be internally-facing and used by other applications/services that we trust.

* We are very focused on automated testing and the tests being a major part of our documentation. Please spend time on testing.

* You do not have to complete this assignment using this scaffolding. You may chose to complete it in any mainstream programming ecosystem. Unfortunately, we do not have scaffolding for all ecosystems so you will have more work to do if you chose an ecosystem we do not support. Please check with us to ensure we can support your choice if you chose to use another ecosystem.

* Comments are a good thing. Please feel free to add your thoughts to parts of the code that could use some context. That will help during our code review and the following technical interview. We are particularly interested in your assumptions and trade-off considerations.

## Questions? ##

If you have questions about the assignment please talk to your recruiter. They will contact the person who can help you and arrange for you to speak with them.
