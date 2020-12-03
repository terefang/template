# template-cli

Template-cli is a linux commandline tool for processing 
various template files using various data files.

**Template Engines**

* jinjava (.j2)
* thymeleaf (.tl)
* freemarker (.fm)
* jexl -- ie. apache commons jexl jxt (.jxt)
* gsimple -- ie. groovy simple-template-engine (.gst)

**Data Formats**

* yaml/yml -- ie. YAML 1.1 -- https://yaml.org/spec/1.1/current.html
* toml/tml -- ie. TOML 0.4 -- https://github.com/toml-lang/toml/blob/master/versions/en/toml-v0.4.0.md
* json -- ie. JSON -- https://www.json.org/json-en.html
* hson/hjson -- ie. human JSON -- https://hjson.github.io/

### standard mode 

eg. render many templates with one data context with optional local context data

```
--template-engine [THYMELEAF, JINJAVA, JEXL, GROOVY, FREEMARKER]
--engine-mode STANDARD
--output-type [TEXT, XML, HTML, JAVASCRIPT]
--additional-context-file FILE
--additional-context-root context
--source-directory DIR
--include [**/*.tl, **/*.j2, **/*.jxt, **/*.gst, **/*.fm]
--destination-directory DIR
--process-local-context
--local-context-extensions ".yaml .yml .json .hson .hjson .toml .tml"
--local-context-root local
```

### template mode 

eg. render one template against many data contexts

```
--template-engine [THYMELEAF, JINJAVA, JEXL, GROOVY, FREEMARKER]
--engine-mode TEMPLATE
--output-type [TEXT, XML, HTML, JAVASCRIPT]
--additional-context-file FILE
--additional-context-root context
--source-directory DIR
--include [**/*.yaml, **/*.yml, **/*.json, **/*.hson, **/*.hjson, **/*.toml, **/*.tml]
--destination-directory DIR
--template-file FILE
--destination-extension EXTENSION
```

## Examples

a number of examples will be growing within the examples directory.

# template-maven-plugin

the maven plugin allows the same functionality as the cli tool

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
    <groupId>com.github.terefang.template</groupId>
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
    <groupId>com.github.terefang.template</groupId>
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
