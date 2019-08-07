library(MASS)
x1<-rnorm(20,mean = 10,sd = 3.33)
y1<-rnorm(20,mean = 10,sd = 3.33)
x2<-runif(10,-2,4)
y2<-runif(10,-2,4)
xy<-cbind(c(x1, x2),c(y1, y2))
xy
cl<-kmeans(xy,2)
n<-30
n.train<-floor(n*0.7)#количество наблюдений для обучения
n.test<-n-n.train#количество наблюдений для тестирования
idx.train<-sample(1:n,n.train)
idx.test<-(1:n)[!(1:n %in% idx.train)]
data.train<-xy[idx.train,]
data.test<-xy[idx.test,]
cl.cluster<-cl$cluster

cl.train<-cl.cluster[idx.train]
cl.test<-cl.cluster[idx.test]
model<-qda(data.train, cl.train) 
cl.test_est<-predict(model, data.test)$class
#оценим ошибку классификации
sum(cl.test_est!=cl.test)/n.test
idw<-idx.test[cl.test_est!=cl.test]
idw
plot(xy, type="n")
legend("topleft",legend=c("1","2"),fill=c("blue","green"))
points(xy[idx.train,],pch=2, col=ifelse(cl.train==1,"blue","green")) #70% - обучающ
points(xy[idx.test,],pch=1, col=ifelse(cl.test==1,"blue","green"))#30% - тестов
points(xy[idw,],col="red", pch=3)#неверно классифицир в рез дискриминантн анализа

idd<-sample(1:n.train,n.train * 0.2)
for(i in idd) {
  if(cl.train[i]==1) {cl.train[i]<-2}
  else {cl.train[i]<-1};
}
model<-qda(data.train, cl.train) 
cl.test_est<-predict(model, data.test)$class
sum(cl.test_est!=cl.test)/n.test
idw<-idx.test[cl.test_est!=cl.test]
idw
plot(xy, type="n")
legend("topleft",legend=c("1","2"),fill=c("blue","green"))
points(xy[idx.train,],pch=2, col=ifelse(cl.train==1,"blue","green")) 
points(xy[idx.test,],pch=1, col=ifelse(cl.test==1,"blue","green"))
points(xy[idw,],col="red", pch=3)
points(xy[idx.train[idd],],pch=3) 
