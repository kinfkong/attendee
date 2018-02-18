COLLECTIONS_TO_DUMP="conversation country user_permission user_role designation file notification_type file_category event_category event_type user_permission user access_token timezone event event_session day_agenda"

for c in $COLLECTIONS_TO_DUMP
do
    java -jar target/attendee-dbtool.jar dump $c
done