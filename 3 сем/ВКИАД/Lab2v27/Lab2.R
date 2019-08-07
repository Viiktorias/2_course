dat=read.table("in.txt", dec = ",")
d=dat[,1]
d
mean(d)#среднее
disp=var(d)#дисперсия
disp
sqrt(disp)#среднее квадратическое
which.max(table(d))#мода
median(d)#медиана
a <- table(d)
a
sr_znach <- mean(d)
sr_znach
n <- length(d)
n
srednekvadr <- sd(d)
srednekvadr
koeff_assim <- sum((d - sr_znach)^3)/(n * srednekvadr^3)
koeff_assim
library(fBasics)
skewness(d) # возвращает коэфф. ассиметрии выборки
kurtosis(d) # возвращает коэфф. эксцесса выборки
zn<-sort(d)
zn
oeff_exxes<-sum((zn - sr_znach)^4)/(n*srednekvadr^4)-3
oeff_exxes
s_sr<-mean(d,trim=2/47,na.rm=TRUE)
s_sr
oeff_var<-disp/sr_znach
oeff_var
tn_lin_otkl<-(sum(abs(zn-sr_znach))/n)/sr_znach
tn_lin_otkl

