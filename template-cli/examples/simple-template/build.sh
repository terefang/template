#!/usr/bin/bash

BIN=../../target/bin/template-cli.*.sh.bin

$BIN \
    --template-engine JINJAVA       \
    --engine-mode TEMPLATE          \
    --template-file ./template.j2   \
    --output-type TEXT              \
    --source-directory ./           \
    --include '*.hson'              \
    --destination-directory ./      \
    --destination-extension .txt