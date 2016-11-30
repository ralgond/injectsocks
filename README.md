# injectsocks
displace "new Socket()" to "new Socket(proxy)" using by asm

execute "mvn clean assembly:assembly" to build

example: test.bat

To solve the asm lib conflicting problem, our classes is loaded by a classloader whose parent is ext-classloader while the other asm lib is loaded by app-classloader.
