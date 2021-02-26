#!/usr/bin/env bash
bDATE=$(date '+%Y%m%d%H%M%S')
bDIR=$(dirname $0)
bDIR=$(cd $bDIR && pwd)

#OPTS="-DskipTests=true -DproxySet=true -DproxyHost=127.0.0.1 -DproxyPort=3128 -Dhttps.nonProxyHosts=127.0.0.1"
OPTS="-DskipTests=true"

while test ! -z "$1" ; do
  case "$1" in
    -major*)
      (cd $bDIR && mvn build-helper:parse-version versions:set \
                -DnewVersion="\${parsedVersion.nextMajorVersion}.1.0" ) || exit 1
      ;;
    -minor*)
      (cd $bDIR && mvn build-helper:parse-version versions:set \
                -DnewVersion="\${parsedVersion.majorVersion}.\${parsedVersion.nextMinorVersion}.0" ) || exit 1
      ;;
    -release*)
      (cd $bDIR && mvn -X build-helper:parse-version versions:set \
                -DnewVersion="\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion}" ) || exit 1
      ;;
    -build*)
      (cd $bDIR && mvn -X buildnumber:create build-helper:parse-version versions:set \
                -DnewVersion="\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.incrementalVersion}-\${buildNumber}" ) || exit 1
      ;;
    -snapshot*)
      (cd $bDIR && mvn -X buildnumber:create build-helper:parse-version versions:set \
                -DnewVersion="\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.incrementalVersion}-rc$bDATE" ) || exit 1
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
        cd $bDIR && scp target/*.rpm root@reposp.ucs.lan.at:/data/repos/shared/rpms/

        for x in /data/repos/shared/; do
          ssh root@reposp.ucs.lan.at "createrepo -s sha -v -v -v ${x}"
        done
      ;;
    *) echo "unknow option ..."
      ;;
  esac
  shift
done