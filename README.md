Module 1<br>
Reflection 1:
Rewriting the code in the module helped me better understand the applications of what we learned. However, I ran into method bugs that I didn't know how to solve with the time I had left for this module. I will hopefully resolve the issues after the deadline, but for now this only contains the code given in the module.

Reflection 2: <br>
Again, I faced some issues with my code so I wasn't able to write my own tests, but after studying this module, Im confident that I can answer these questions.
1. I wasn't able to write any of my own tests, but I think I get the general idea. It depends, but generally speaking you should have at least one test for each method in a class. We can use tools to measure test coverage, we should test edge cases and boundary conditions, and peer reviews. Having 100% code coverage only means that every line of code has been executed in your tests, but it doesn't mean that your code is free of bugs or errors because that depends on the quality of your tests.
2. There's a risk of code duplication in the new suite because the setup procedures and instance variables from existing suites, violating the DRY principle. Additionally, while the test method names are descriptive, they could be more specific to clearly communicate the aspects of functionality being tested. Hardcoded values like the expected page title and welcome message could be made into constants to improve readability and maintainability. The handling of exceptions in the test methods could be made better to catch specific exceptions, providing clearer error messages.

   To improve code cleanliness and maintain code quality, we could place the code in a single file to be imported into others to avoid duplication. Ensure that test method names are descriptive and specific, clearly communicating the functionality being tested. Use constants for hardcoded values to improve readability and maintainability. Imporve error handling by catching specific exceptions to provide clearer error messages, and ensure that each test method is independent and does not rely on the state or behavior of other tests.

Module 2 <br>
Reflection 1: I'm going to spend more time over the weekend to clean up this module. I had a lot of difficulties understanding and implementing these new concepts. Because of this, I only have one code quality issue which was the inconsistency of the java versions in the build.gradle.kts file.

Reflection 2: The current implementation has met the definition of continuous integration. The workflows are triggered on pushes to the main branch and pull request actions. This ensures that code changes are regularly integrated and tested. This allows us to find bugs earlier and makes maintenance easier. Even though not all the workflows directly handle deployment, they contribute to the CI/CD pipeline by automating tasks such as security analysis, code quality checks, and test execution. Therefore, streamlining the development lifecycle by enabling developers to deliver high-quality code more efficiently and reliably.

Module 3 <br>
Reflection: I directly applied OCP and LSP. The advantage of OCP is less maintenance cost because we make our code so that it should only be extended and not modified. The advantage of LSP is that it makes our code more flexible since objects of a superclass should be interchangeable with objects of its subclasses. For example, the car class shouldn't extend the product class. The disadvatages of not apply SOLID principles is that it becomes less flexible, hard to maintain and scale, loss of test efficiency, and extra work. I have only applied two principles so far in my project, so I need to add more to avoid these issues.

Module 4 <br>
Reflection:
1. Self-reflective questions
- "Do I have enough functional tests to reassure myself that my application really works from the point of view of the user?": In this module, we focused on creating unit tests to implement a model, repository, and service. Therefore, I didn't implement any new functional tests, but I imagine we will work on that in the upcoming modules.
- "Am I testing all the edge cases thoroughly?": I believe I checked at least most of the edge cases for the payment classes. I tested for different possible scenarios for each method (valid, invalid, and empty arguments).
- "Do I have tests that check whether all my components fit together properly? Could some integrated tests do this, or are functional tests enough?": We didn't implement functional tests for this module, but I believe all types of tests are necessary to ensure our code functions as intended on a macro or micro scale.
2. F.I.R.S.T. Principle
- The unit tests are fast and do not take much time to run. Each test is isolated and does not affect other tests. The tests are repeatable because they are independent. Assert methods are used at the end of every test to self-validate happy or unhappy scenarios. According to SonarCloud, the current code coverage is 90%. I believe I thoroughly made my tests to ensure its functionality.