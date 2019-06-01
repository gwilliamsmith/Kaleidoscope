localMaxima <- function(x) {
  # Use -Inf instead if x is numeric (non-integer)
  y <- diff(c(-Inf, x)) > 0L
  rle(y)$lengths
  y <- cumsum(rle(y)$lengths)
  y <- y[seq.int(1L, length(y), 2L)]
  if (x[[1]] == x[[2]]) {
    y <- y[-1]
  }
  y
}

localMinima <- function(x) {
  # Use -Inf instead if x is numeric (non-integer)
  y <- diff(c(Inf, x)) > 0L
  rle(y)$lengths
  y <- cumsum(rle(y)$lengths)
  y <- y[seq.int(1L, length(y), 2L)]
  if (x[[1]] == x[[2]]) {
    y <- y[-1]
  }
  y
}

createVariancePlot <- function(x,testName){
	results <- read.csv(x,head=TRUE,sep=",");
	tester <- loess(results$LifespanVariation ~ results$RecordID, span = .05)
	temp <- predict(tester)
	maintitle <- paste("Lifespan Variation for ",testName)
	plot(c(0,max(results$RecordID)),c(0,max(results$LifespanVariation)),main=maintitle,xlab="Steps/50",ylab="Variation",type="n")
	lines(results$RecordID,results$LifespanVariation, col="lightgray",type="p")
	lines(results$RecordID,temp,col="green")
	loessMaxima<-localMaxima(temp)
	loessMinima<-localMinima(temp)
	for(i in 1:length(loessMaxima)){
		abline(v=loessMaxima[i],col="red")
	}
	for(i in 1:length(loessMinima)){
		abline(v=loessMinima[i],col="blue")
	}
	legend("topleft",legend=c("Raw Data","Loess Smoothed Line","Maxima Location","Minima Location"),lty=c(1,1),col=c("lightgray","green","red","blue"))
	out <- list(loessMaxima,loessMinima,results$LifespanVariation)
	return(out)
}

createDeviationPlot <- function(x){
	results <- read.csv(x,head=TRUE,sep=",");
	tester <- loess(results$LifespanDeviation ~ results$RecordID, span = .05)
	temp <- predict(tester)
	plot(results$RecordID,results$LifespanDeviation, main = "Lifespan Varaiability for Test1", xlab ="Steps/50", ylab ="Deviation", col="lightgray")
	lines(results$RecordID,temp, col="green")
	loessMaxima<-localMaxima(temp)
	loessMinima<-localMinima(temp)
	for(i in 1:length(loessMaxima)){
		abline(v=loessMaxima[i],col="red")
	}
	for(i in 1:length(loessMinima)){
		abline(v=loessMinima[i],col="blue")
	}
	out <- list(loessMaxima,loessMinima,results$LifespanDeviation,results$FamilyPorportion)
	return(out)
}

maximaDistance <- function(x){
	y <- numeric()
	for(i in 1:length(x)){
		if(i <length(x)){
			z <- x[i+1] - x[i]
			y <- c(y,z)
		}	
	}
	return(y)
}

maximaMinimaDistance <- function(maxima,minima){
	out <- double()
	outIndex <- 0
	minimaIndex <- 1
	maximaIndex <- 1
	maximaLimit <- length(maxima)
	if(maxima[maximaIndex] > minima[minimaIndex]){
		minimaIndex <- minimaIndex+1
	}
	if(maxima[maximaLimit] > minima[length(minima)]){
		maximaLimit <- maximaLimit-1
	}
	for(i in 1:maximaLimit){
		temp <- minima[minimaIndex] - maxima[maximaIndex]
		minimaIndex <- minimaIndex+1
		maximaIndex <- maximaIndex+1
		out <- c(out,temp)
	}
	return(out)
}

plotMaxMinDistances <- function(x){
	maximaDistances <- maximaDistance(x[[1]])
	maximaMinimaDistances <- maximaMinimaDistance(x[[1]],x[[2]])
	plot(maximaDistances,
		col="red",
		type="b",
		xlim=c(0,length(maximaDistances)),
		ylim=c(0,max(max(maximaDistances),max(maximaMinimaDistances))),
		main="Maxima/Minima Distances",
		xlab="Time",
		ylab="Distance in steps/50")
	lines(maximaMinimaDistances,col="blue",type="b")
	legend("topleft",legend=c("Distance between maxima","Distance between maxima-minima pairs"),lty=c(1,1),col=c("red","blue"))
}

plotMaxMinValueDifference <- function(x){
	out <- double()
	maxima <- x[[1]]
	minima <- x[[2]]
	data <- x[[3]]
	outIndex <- 0
	minimaIndex <- 1
	maximaIndex <- 1
	maximaLimit <- length(maxima)
	if(maxima[maximaIndex] > minima[minimaIndex]){
		minimaIndex <- minimaIndex+1
	}
	if(maxima[maximaLimit] > minima[length(minima)]){
		maximaLimit <- maximaLimit-1
	}
	for(i in 1:maximaLimit){
		temp <- data[maxima[maximaIndex]] - data[minima[minimaIndex]]
		minimaIndex <- minimaIndex+1
		maximaIndex <- maximaIndex+1
		out <- c(out,temp)
	}
	plot(out,
		col="red",
		type="b",
		main="Difference variance in maxima-minima pairs",
		xlab="Time",
		ylab="Difference in variance")
	return(out)
}

getProportionalPlot <- function(x,y){
	a <- read.csv(x,head=TRUE,sep=",")
	b <- read.csv(y,head=TRUE,sep=",")
	plotProportionalPopulation(a$FamilyProportion,b$FamilyProportion)
}

plotProportionalPopulation <- function(pop1,pop2){
	pop1Index <- 1
	pop2Index <- 1
	pop1Prev <- -1
	pop2Prev <- -1
	for(i in 1:length(pop1)){
		if(pop1Prev == -1){
			pop1Prev <- pop1[i]
		}
		else{
			if((pop1Prev == 1 && pop1[i] == 1) || (pop1Prev == 0 && pop1[1] == 0)){
				break
			}
		}
		pop1Prev <-pop1[i]
		pop1Index <- pop1Index + 1
	}
	for(i in 1:length(pop2)){
		if(pop2Prev == -1){
			pop2Prev <- pop2[i]
		}
		else{
			if((pop2Prev == 1 && pop2[i] == 1) || (pop2Prev == 0 && pop2[1] == 0)){
				break
			}
		}
		pop2Prev <- pop2[i]
		pop2Index <- pop2Index + 1
	}
	maxLength <- min(pop1Index,pop2Index) +5
	pop1Slice <- pop1[1:maxLength]
	pop2Slice <- pop2[1:maxLength]
	plot(pop1Slice,
		col="red",
		type="l",
		xlim=c(1,maxLength),
		ylim=c(0,1),
		main="Population proportions",
		xlab="Time",
		ylab="% of total lines")
	lines(pop2Slice,col="blue",type="l")
	legend("topleft",legend=c("Family 1","Family 2"),lty=c(1,1),col=c("red","blue"))
}

oneFamilyTestBattery <- function(x,testName){
	oldPar <- par()
	par(mfrow=c(3,1))
	varianceData <- createVariancePlot(x,testName)
	plotMaxMinDistances(varianceData)
	plotMaxMinValueDifference(varianceData)
}

twoFamilytestBattery <- function(x,y,testName){
	oldPar <- par()
	par(mfrow=c(4,2))
	family1VarianceData <- createVariancePlot(x,testName)
	family2VarianceData <- createVariancePlot(y,testName)
	plotMaxMinDistances(family1VarianceData)
	plotMaxMinDistances(family2VarianceData)
	plotMaxMinValueDifference(family1VarianceData)
	plotMaxMinValueDifference(family2VarianceData)
}