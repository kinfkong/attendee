#!/usr/bin/env bash

COMPONENT_NAME="Email"
COMPONENT_H_NAME="email"
COMPONENT_URL="emails"
COMPONENT_VAR_NAME="email"

GENERATE_TARGET_DIR=./src/main/java/com/

TEMPLATE_NAME="EventCategory"
TEMPLATE_H_NAME="event category"
TEMPLATE_URL="eventCategories"
TEMPLATE_VAR_NAME="eventCategory"

TEMPLATE_DIR=./../templates
WORKING_TEMPLATE_DIR=./../working-templates

rm -rf $WORKING_TEMPLATE_DIR
cp -r $TEMPLATE_DIR $WORKING_TEMPLATE_DIR

FILES=`find $WORKING_TEMPLATE_DIR -name "*.java"`
for file in $FILES
do
    SEDEXP="s/$TEMPLATE_NAME/$COMPONENT_NAME/g"
    TARGETFILE=`echo $file | sed $SEDEXP`
    mv $file $TARGETFILE

    sed -i.bak "s/$TEMPLATE_NAME/$COMPONENT_NAME/g" $TARGETFILE

    sed -i.bak "s/$TEMPLATE_H_NAME/$COMPONENT_H_NAME/g" $TARGETFILE

    sed -i.bak "s/$TEMPLATE_URL/$COMPONENT_URL/g" $TARGETFILE

    sed -i.bak "s/$TEMPLATE_VAR_NAME/$COMPONENT_VAR_NAME/g" $TARGETFILE

    echo $TARGETFILE
done
find $WORKING_TEMPLATE_DIR -name "*.bak" -exec rm -f {} \;

cp -rn $WORKING_TEMPLATE_DIR/* $GENERATE_TARGET_DIR
