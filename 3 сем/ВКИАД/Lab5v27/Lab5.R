dat=read.table("input.txt", dec='.')
plot(dat,type="p",main="Диаграмма рассеяния",xlab="X", ylab="Y")
dat


dat[,1]<-dat[,1]/mean(dat[,1])  
dat[,2]<-dat[,2]/mean(dat[,2])

cl1<-kmeans(dat,2)
table(cl1$cluster)
cl1$centers

cl2<-kmeans(dat,3)
table(cl2$cluster)
cl2$centers
plot(dat,col=ifelse(cl1$cluster==1,"blue","green"), pch=ifelse(cl2$cluster==1,1, ifelse(cl2$cluster==2, 2, 3)))
legend("topleft",legend=c("1","2"),fill=c("blue","green"))
legend("bottomright",legend=c("1","2","3"),pch=c(1,2,3))