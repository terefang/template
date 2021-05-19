#!/bin/sh
MYSELF=`which "$0" 2>/dev/null`
[ $? -gt 0 -a -f "$0" ] && MYSELF="./$0"
MYDIR=$(dirname $MYSELF)
MYEXE=$(basename $MYSELF)

if test "x$1" = "x-install"; then
    shift
    IDIR=$(cd $MYDIR && pwd)
    for e in jexl jxlt ecma gsimple jinjava freemarker thymeleaf trimou; do
        for m in std template; do
            cd $IDIR && ln -s ./$MYEXE $e-$m-proc
        done
    done
    cd $IDIR && ln -s ./$MYEXE preprocessor-proc
    cd $IDIR && ln -s ./$MYEXE script-proc
    exit 0;
fi

if test "x$1" = "x-java-term" -o "x$1" = "x-JT"; then
    shift
    export TERM="$1"
    shift
fi

if test "x$1" = "x-java-home" -o "x$1" = "x-JH"; then
    shift
    export JAVA_HOME="$1"
    shift
fi

if test "x$1" = "x-java-opts" -o "x$1" = "x-JO"; then
    shift
    export _JAVA_OPTS="$1"
    shift
fi

if test ! -n "$JAVA_HOME"; then
    if test -d "$MYDIR/java"; then
        export JAVA_HOME="$MYDIR/java"
    elif test -d "$MYDIR/jre"; then
        export JAVA_HOME="$MYDIR/jre"
    elif test -d "$MYDIR/../java"; then
        export JAVA_HOME="$MYDIR/../java"
    elif test -d "$MYDIR/../jre"; then
        export JAVA_HOME="$MYDIR/../jre"
    fi
fi

java=java
if test -n "$JAVA_HOME"; then
    java="$JAVA_HOME/bin/java"
fi

if test "x$1" = "x"; then
    exec "$java" -jar $MYSELF
    exit 1
fi

if test "x$MYEXE" = "xjexl-std-proc"; then
      EXEARGS=" -T JEXL -M STANDARD "
elif test "x$MYEXE" = "xjexl-template-proc"; then
         EXEARGS=" -T JEXL -M TEMPLATE "
elif test "x$MYEXE" = "xjxlt-std-proc"; then
         EXEARGS=" -T JXLT -M STANDARD "
elif test "x$MYEXE" = "xjxlt-template-proc"; then
         EXEARGS=" -T JXLT -M TEMPLATE "
elif test "x$MYEXE" = "xecma-std-proc"; then
         EXEARGS=" -T ECMA -M STANDARD "
elif test "x$MYEXE" = "xecma-template-proc"; then
         EXEARGS=" -T ECMA -M TEMPLATE "
elif test "x$MYEXE" = "xgsimple-std-proc"; then
         EXEARGS=" -T GROOVY -M STANDARD "
elif test "x$MYEXE" = "xgsimple-template-proc"; then
         EXEARGS=" -T GROOVY -M TEMPLATE "
elif test "x$MYEXE" = "xjinjava-std-proc"; then
         EXEARGS=" -T JINJAVA -M STANDARD "
elif test "x$MYEXE" = "xjinjava-template-proc"; then
         EXEARGS=" -T JINJAVA -M TEMPLATE "
elif test "x$MYEXE" = "xfreemarker-std-proc"; then
         EXEARGS=" -T FREEMARKER -M STANDARD "
elif test "x$MYEXE" = "xfreemarker-template-proc"; then
         EXEARGS=" -T FREEMARKER -M TEMPLATE "
elif test "x$MYEXE" = "xthymeleaf-std-proc"; then
         EXEARGS=" -T THYMELEAF -M STANDARD "
elif test "x$MYEXE" = "xthymeleaf-template-proc"; then
         EXEARGS=" -T THYMELEAF -M TEMPLATE "
elif test "x$MYEXE" = "xtrimou-std-proc"; then
         EXEARGS=" -T TRIMOU -M STANDARD "
elif test "x$MYEXE" = "xtrimou-template-proc"; then
         EXEARGS=" -T TRIMOU -M TEMPLATE "
elif test "x$MYEXE" = "xpreprocessor-proc"; then
         EXEARGS=" -T PREPROCESSOR -M STANDARD "
elif test "x$MYEXE" = "xscript-proc"; then
         EXEARGS=" -T SCRIPT -M SCRIPT "
fi

exec "$java" $_JAVA_OPTS -jar $MYSELF $EXEARGS "$@"
exit 1