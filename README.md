# trains

**MainClass:** icezhg.trains.Trains
```CMD
D:\Documents\project\trains\src\main\java>javac icezhg\trains\*.java
D:\Documents\project\trains\src\main\java>java icezhg.trains.Trains D:\Documents\project\trains\input
```
```CMD
D:\Documents\project\trains>mvn clean package
D:\Documents\project\trains>java -jar target\trains.jar input
```

## 数据结构
### 矩阵 Matrix
    n 阶方阵, n 取决于 input 文件中节点个数
    m 行 n 列的元素 d(mn). d > 0, 表示节点 m 和 n 之间存在距离为 d 的有向路径; d = 0 表示节点 m 和 n 之间不存在路径
### 图 Graph
    NODE 节点集(List), 表示节点名称与矩阵索引之间的映射关系
    MATRIX 邻接矩阵(Matris), 表示节点之间的相连关系
    CYCLE_ROUTES 闭环集(Map), 从某个节点出发又回到自身的路径集合
### 路径 Route
    head 路径的起点
    end 路径的终点(无效路径 end 为 null)
    trace 路径(List), trace 中的元素按顺序表示一条路径. 至少包含 head 节点. 无效路径只包含 head 元素
    distance 路径的长度
    stop 路径中的停止点个数
    