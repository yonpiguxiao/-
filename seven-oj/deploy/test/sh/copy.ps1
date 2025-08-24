rm D:/github/seven-oj/deploy/test/sevenoj-jar/gateway/oj-gateway.jar
rm D:/github/seven-oj/deploy/test/sevenoj-jar/friend/oj-friend.jar
rm D:/github/seven-oj/deploy/test/sevenoj-jar/job/oj-job.jar
rm D:/github/seven-oj/deploy/test/sevenoj-jar/judge/oj-judge.jar
rm D:/github/seven-oj/deploy/test/sevenoj-jar/system/oj-system.jar

copy D:/github/seven-oj/oj-gateway/target/oj-gateway-1.0-SNAPSHOT.jar D:/github/seven-oj/deploy/test/sevenoj-jar/gateway/oj-gateway.jar
copy D:/github/seven-oj/oj-modules/oj-judge/target/oj-judge-1.0-SNAPSHOT.jar D:/github/seven-oj/deploy/test/sevenoj-jar/judge/oj-judge.jar
copy D:/github/seven-oj/oj-modules/oj-friend/target/oj-friend-1.0-SNAPSHOT.jar D:/github/seven-oj/deploy/test/sevenoj-jar/friend/oj-friend.jar
copy D:/github/seven-oj/oj-modules/oj-job/target/oj-job-1.0-SNAPSHOT.jar D:/github/seven-oj/deploy/test/sevenoj-jar/job/oj-job.jar
copy D:/github/seven-oj/oj-modules/oj-system/target/oj-system-1.0-SNAPSHOT.jar D:/github/seven-oj/deploy/test/sevenoj-jar/system/oj-system.jar
pause