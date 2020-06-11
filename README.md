# template-maven-plugin

use the jitpack repository

```
<pluginRepositories>
    <pluginRepository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </pluginRepository>
</pluginRepositories>
```

### standard mode 

eg. render many templates with one data context

```
<plugin>
    <artifactId>template-maven-plugin</artifactId>
    <groupId>com.github.terefang</groupId>
    <version>${template.maven.plugin.version}</version>
    <executions>
        <execution>
            <id>standard</id>
            <phase>generate-resources</phase>
            <goals>
                <goal>${engine}-standard</goal>
            </goals>
            <configuration>
                <additionalContext>test.hson</additionalContext>
                <resourcesDirectory>${project.basedir}/src/main/templates</resourcesDirectory>
                <resourcesOutput>${project.build.outputDirectory}/resources</resourcesOutput>
                <processLocalContext>true</processLocalContext>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### template mode 

eg. render one template against many data contexts

```
<plugin>
    <artifactId>template-maven-plugin</artifactId>
    <groupId>com.github.terefang</groupId>
    <version>${template.maven.plugin.version}</version>
    <executions>
        <execution>
            <id>auto-template</id>
            <phase>generate-resources</phase>
            <goals>
                <goal>${engine}-template</goal>
            </goals>
            <configuration>
                <templateFile>${project.basedir}/src/main/resources/base.j2</templateFile>
                <resourcesDirectory>${project.basedir}/src/main/resources</resourcesDirectory>
                <includes>**/*.hson</includes>
                <resourcesOutput>${project.build.outputDirectory}/resources</resourcesOutput>
                <destinationExtension>.md</destinationExtension>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### template engines

the following template engines are supported:

* jinjava
* thymeleaf
* freemarker
* gsimple -- ie. groovy simple-template-engine

### context data files

the following file formats are supported:

* yaml/yml -- ie. YAML 1.1 -- https://yaml.org/spec/1.1/current.html
* toml/tml -- ie. TOML 0.4 -- https://github.com/toml-lang/toml/blob/master/versions/en/toml-v0.4.0.md
* json -- ie. JSON -- https://www.json.org/json-en.html
* hson/hjson -- ie. human JSON -- https://hjson.github.io/

