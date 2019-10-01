# injectsocks
displace "new Socket()" to "new Socket(proxy)" using by asm。


execute "mvn clean assembly:assembly" to build

example: test.bat

To solve the asm lib conflicting problem, our classes is loaded by a classloader whose parent is ext-classloader while the other asm lib is loaded by app-classloader.


## 为什么要这么做呢？

这主要是为了让旧的代码不做任何改变就能享用到socks5服务。

举个例子：eclipse的建立连接的地方非常之多，我们又因为防火长城部署了一台socks5服务器。

那么如何让eclipse的享用这台socks5服务呢，这里就用到injectsocks，这个中间件在启动一条连接时，会为这一条连接注入一条通往socks5服务器的连接。

可以看出整个过程都不需要修改现有代码，而eclipse的连接的是socks5服务器也并非是目标站点，这个socks5服务器会代理eclipse访问目标站点（因为防火长城隔离掉的）。
