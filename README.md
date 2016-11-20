# injectsocks
displace "new Socket" to "new Socket(proxy)" using by asm

execute "mvn assembly:assembly" to build

run:
java -Dinjectsocks.configfile=inject.config -javaagent:target/injectsocks-1.0.0.jar -jar XXXXXX-SNAPSHOT.jar 14.215.177.38 80

