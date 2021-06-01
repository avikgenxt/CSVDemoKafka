<h1>READ ME</h1>

<h2>Services</h2>
<p><b><h3>CSVIngerterMicroservice </h3></b> - Inject 2 CSV using the CSVIngerterMicroservice, map these to a pojo and push the serialized object to different topics using a generic producer. Assumptions - It will be able to read CSV files only, in the given format. If the format of the data changes, then we would need to implement AVRO if we want to use the same topic. .No persistence in the injester, currently running in default kafka config. Written a dummy API to upload files </p>

<p><b><h3>CSVConsumerMicroservice </h3></b>- The service to consume the data from topic, and then write the same to mongo collection, IMO we should have one collection containing both the details so there, so that the fetch is faster, assuming that read will be perform multiple times. That is not implemented as I went by the requirements. I have used Mongo as I have work experience in Mongo DB and also access to one DB. If there are many such relations and high availability is a concern, then we can go for Cassandra, if Json and unorganized structure is a must, then mongo will be better. This will also ensure horizontal scalability.</p>

<p>The consumer also has parallel race condition implemented, although the local one has one partition and this will not kick in, but will work for topics where the partition is more than 1 The consumer also has error handler implemented in case some wrong record is pushed to topic, the record will be ignored.</p>

<p><b><h3>UserQueryService </h3></b>- The service fetches data based on email and/or userid assuming one of them is provided, if there is no details present in any of the collections then there will be blank values. Also has a basic swagger doc implemented</p>

<h3>Scope of improvements <h3>
<ul>
<li>In case the data for users is a high frequency one, we can may be use one topic and push data by partitions.</li>
<li>Store both the data in one collection</li>
<li>Implement AVRO to tackle issues while upgrading the schema</li>
<li>Add spring clouid admin on top of Eureka (added) to help with monitoring</li>
</ul>

