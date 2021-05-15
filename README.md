This minimalistic project serves as a proof of concept tool for detection of cycles in the Spring bean dependency graph.

To run this tool, install this maven project first (e.g. `./mvnw install` in the root directory of this project) and
then execute the built artifact using `java -jar target/demo-0.0.1-SNAPSHOT.jar`.

This tool takes into consideration two ways of dependency injecton:

- in the package `autowired`, a bean dependency cycle is used purely based on `@Autowired` injection
- in the package `constructor`, we declare bean dependencies (and their cycle) using constructor-based DI

Analysis will be shown in the execution output (look for ERROR messages).

Consult `beans.xml` file to see how precisely the Spring context is set up.
