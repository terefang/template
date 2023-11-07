#!/usr/bin/env bash
bDATE=$(date '+%Y%m%d%H%M%S')
yDATE=$(date '+%Y')
mDATE=$(date '+%-m')
bDIR=$(cd $(dirname $0) && pwd)

#OPTS="-DskipTests=true -DproxySet=true -DproxyHost=127.0.0.1 -DproxyPort=3128 -Dhttps.nonProxyHosts=127.0.0.1"
OPTS="-DskipTests=true"

while test ! -z "$1" ; do
  case "$1" in
    -drel*)
        (cd $bDIR && mvn -X build-helper:parse-version versions:set \
                  -DnewVersion="${yDATE}.${mDATE}.\${parsedVersion.nextIncrementalVersion}" ) || exit 1
        ;;
    -setversion*)
      VALUE="${2}"
      (cd $bDIR && mvn build-helper:parse-version versions:set \
                -DnewVersion="${VALUE}" ) || exit 1
      shift
      ;;
    -setmajor*)
      VALUE="${2}"
      (cd $bDIR && mvn build-helper:parse-version versions:set \
                -DnewVersion="${VALUE}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion}" ) || exit 1
      shift
      ;;
    -major*)
      (cd $bDIR && mvn build-helper:parse-version versions:set \
                -DnewVersion="\${parsedVersion.nextMajorVersion}.1.\${parsedVersion.nextIncrementalVersion}" ) || exit 1
      ;;
    -setminor*)
      VALUE="${2}"
      (cd $bDIR && mvn build-helper:parse-version versions:set \
                -DnewVersion="\${parsedVersion.majorVersion}.${VALUE}.\${parsedVersion.nextIncrementalVersion}" ) || exit 1
      shift
      ;;
    -minor*)
      (cd $bDIR && mvn build-helper:parse-version versions:set \
                -DnewVersion="\${parsedVersion.majorVersion}.\${parsedVersion.nextMinorVersion}.\${parsedVersion.nextIncrementalVersion}" ) || exit 1
      ;;
    -setrelease*)
      VALUE="${2}"
      (cd $bDIR && mvn -X build-helper:parse-version versions:set \
                -DnewVersion="\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.${VALUE}" ) || exit 1
      shift
      ;;
    -release*)
      (cd $bDIR && mvn -X build-helper:parse-version versions:set \
                -DnewVersion="\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion}" ) || exit 1
      ;;
    -clean*)
      (cd $bDIR && mvn clean $OPTS -U) || exit 1
      ;;
    -pack*)
      (cd $bDIR && mvn clean package $OPTS -U) || exit 1
      ;;
    -fix*)
      (cd $bDIR && mvn -N versions:update-child-modules) || exit 1
      ;;
    -install*)
      (cd $bDIR && mvn clean install $OPTS) || exit 1
      (test -e ~/bin/template-cli.sh && cd $bDIR && cp template-cli/target/bin/template-cli*.bin ~/bin/template-cli.sh)
      ;;
    -deps)
      (cd $bDIR && mvn org.apache.maven.plugins:maven-dependency-plugin:tree) || exit 1
      ;;
    *) echo "unknow option ..."
      ;;
  esac
  shift
done