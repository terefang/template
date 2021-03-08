# template-cli

Template-cli is a linux commandline tool for processing 
various template files using various data files.

**Template Engines**

* jinjava (.j2)
* thymeleaf (.tl)
* freemarker (.fm)
* jexl -- ie. apache commons jexl jxt (.jxt) or plain jexl (.jx)
* ecma -- ie. mozilla rhino javascript (.js/.ecma) or asp-style (.esp)
* gsimple -- ie. groovy simple-template-engine (.gst)
* groovy -- ie. groovy script with redirected stdout (.groovy)
* luaj -- ie. lua script with out printwriter (.lua)
* handlebars -- (.hbs)
* trimou -- ie. advanced handlebars (.tri)
* apache velocity -- (.vm, .vt)

**Gfx Formats**

*Only groovy and luaj are currently able to produce gfx.*

* PNG
* SVG

**Data Formats**

* java properties
* yaml/yml -- ie. YAML 1.1 -- https://yaml.org/spec/1.1/current.html
* toml/tml -- ie. TOML 0.4 -- https://github.com/toml-lang/toml/blob/master/versions/en/toml-v0.4.0.md
* json -- ie. JSON -- https://www.json.org/json-en.html
* hson/hjson -- ie. human JSON -- https://hjson.github.io/
* ini -- simple windows ini file format
* csv/list/scsv/tsv format files, under 'data' context 
* txt format files (line by line), under 'data' context
* pdx/pdata -- Paradox Config Format (Clausewitz Engine) with some quality of life extensions ( arrays, cardinals, hexadecimal )

```
#One struct
struct1 = {
    id = test_1
    desc = "test description"
}
#Another struct
struct2 = { id = test_2 desc = "test 2 description" }
#complex struct
struct2 = {
    string_array = [ one two "three" ]
    int_array = [  1 2 3 ]
    float_array = [ 0.1 0.2 0.3 ]
    long_text = """ 
                    very long text
    """
    struct_array = [
        { hex1_key = 0xdeadbeef hex2_key = 0xcafeaffe hex3_key = 0xf hex4_key = 0xff hex5_key = 0xfff }
        { dec1_key = 0 dec2_key = 01 } { float1_key = 0.1 float2_key = .1 }
        { 1 = "one" 2 = "two" "3" = "three" } ]
    complex_array = [ 1 .2 "three" 0x4 five ]
}
```

### standard mode 

eg. render many templates with one data context with optional local context data

```
--template-engine [THYMELEAF, JINJAVA, JXLT, JEXL, GSIMPLE, GROOVY, LUAJ, FREEMARKER, TRIMOU, HANDLEBARS, VELOCITY]
--engine-mode STANDARD
--output-type [TEXT, XML, HTML, JAVASCRIPT, SVG, PNG]
--additional-context-file FILE
--additional-context-root context
--source-directory DIR
--include [**/*.tl, **/*.j2, **/*.jxt, **/*.gst, **/*.fm]
--destination-directory DIR
--flatten
--process-local-context
--local-context-extensions ".yaml .yml .json .hson .hjson .toml .tml .ini .pdx .pdata"
--local-context-root local
```

### template mode 

eg. render one template against many data contexts

```
--template-engine [THYMELEAF, JINJAVA, JXLT, JEXL, GSIMPLE, GROOVY, LUAJ, FREEMARKER, TRIMOU, HANDLEBARS, VELOCITY]
--engine-mode TEMPLATE
--output-type [TEXT, XML, HTML, JAVASCRIPT, SVG, PNG]
--additional-context-file FILE
--additional-context-root context
--source-directory DIR
--include [**/*.yaml, **/*.yml, **/*.json, **/*.hson, **/*.hjson, **/*.toml, **/*.tml, **/*.pdx, **/*.pdata]
--destination-directory DIR
--flatten
--template-file FILE
--destination-extension EXTENSION
```

### pre-processor

implements a simple extractor/pre-processor, 
useful if you want to keep several different structures in one file

eg. pre-process multiple file into one or multiple

**Sample File:**
```
%!begin MARK

extracted content

%!end MARK

%% single line extract

%!include path/to/file.ext
```

#### single file target

if --single-file is given destination is a single file

```
--template-engine 'PREPROCESSOR'
--source-directory DIR
--include [**/*.pp]
--destination DIRorFile
--single-file
--marker MARK
--process-includes
--single-line-marker
```

#### multi file target

```
--template-engine PREPROCESSOR
--source-directory DIR
--include [**/*.pp]
--destination-directory DIR
--flatten
--destination-extension EXTENSION
--marker MARK
--process-includes
--single-line-marker
```

### concatenation

```
--template-engine 'CONCAT'
--source-directory DIR
--include [**/*.pp]
--destination FILE
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

### pre-process

```
<plugin>
    <artifactId>template-maven-plugin</artifactId>
    <groupId>com.github.terefang.template</groupId>
    <version>${template.maven.plugin.version}</version>
    <executions>
        <execution>
            <id>pre-processor</id>
            <phase>generate-resources</phase>
            <goals>
                <goal>pre-processor</goal>
            </goals>
            <configuration>
                <resourcesDirectory>${project.basedir}/src/main/resources</resourcesDirectory>
                <includes>**/*.pp</includes>
                <resourcesOutput>${project.build.outputDirectory}/resources</resourcesOutput>
                <marker>MARKDOWN</marker>
                <destinationExtension>.md</destinationExtension>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### concatenate

```
<plugin>
    <artifactId>template-maven-plugin</artifactId>
    <groupId>com.github.terefang.template</groupId>
    <version>${template.maven.plugin.version}</version>
    <executions>
        <execution>
            <id>concat</id>
            <phase>generate-resources</phase>
            <goals>
                <goal>concat</goal>
            </goals>
            <configuration>
                <resourcesDirectory>${project.basedir}/src/main/resources</resourcesDirectory>
                <includes>**/*.pp</includes>
                <resourcesOutput>${project.build.outputDirectory}/concat-file.ext</resourcesOutput>
            </configuration>
        </execution>
    </executions>
</plugin>
```
