# topbank-lib

How to use library in project :
- Add maven repository in project pom.xml
```
  <repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
  </repositories>
  ```
- Add the dependency
```
  <dependency>
  <groupId>com.github.stephendanielxxx</groupId>
  <artifactId>topbank-lib</artifactId>
  <version>1.0.3</version>
  </dependency>
```
How to update library
- Apply changes in project
- Push to Github
- Merge changes to master branch
- Create new tag and release
- Go to jitpack.io
- Check if current release is successfully built
- Update version in project
- Reload pom.xml