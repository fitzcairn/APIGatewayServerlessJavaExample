# APIGatewayServerlessJavaExample

This is a simple framework for implementing a serverless RESTful API fronted by [API Gateway](https://aws.amazon.com/api-gateway/) and powered by [AWS Lambda](https://aws.amazon.com/lambda/) functions written in Java 8.

The sample implementation is an API backing [GoalTender](https://github.com/fitzcairn/GoalTender) (a React Native app), using [DynamoDb](https://aws.amazon.com/dynamodb/) as a storage provider.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Unfortunately, you're on your own with regards to building your own production environment.  Luckily, there are lots of guides out there; [here is one I found particularly helpful](https://blog.codecentric.de/en/2018/04/accessing-aws-resources-with-google-sign-in/).

### Prerequisites

I suggest you do the following before cloning this project:

* Download the [IntelliJ IDEA](https://www.jetbrains.com/idea/download).
* Ensure you have [Java JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed.
* Create a [GitHub](https://github.com/) account.
* Set up [GitHub over SSH](https://help.github.com/articles/connecting-to-github-with-ssh/).

### Building Locally

* Clone this repository to your local machine (directions [here](https://help.github.com/articles/cloning-a-repository/)).
* I strongly suggest [creating a local branch](https://git-scm.com/book/en/v2/Git-Branching-Basic-Branching-and-Merging) before making any changes.
* Open IntelliJ and[import the Maven project](https://www.lagomframework.com/documentation/1.4.x/java/IntellijMaven.html).
* To build a jar with dependencies (suitable for deploying to a AWS lambda), use the [Maven Assembly Plugin](https://maven.apache.org/plugins/maven-assembly-plugin/usage.html).

## Running Tests

There is a reasonably full suite of tests for this repository; run them via IntelliJ or Maven.

## Contributing

I rather like this [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426); let's follow that for any pull requests.  Improvements always welcome--this codebase has a lot of room to get better.

## Versioning

I use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/fitzcairn/APIGatewayServerlessJavaExample/tags).

## Authors

* **Steve Martin** - *Initial work* - [Fitzcairn](https://github.com/fitzcairn)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details