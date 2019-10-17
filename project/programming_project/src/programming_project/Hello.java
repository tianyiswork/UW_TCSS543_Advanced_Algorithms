package programming_project;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Hello {
	public static final BigInteger one=BigInteger.ONE;
	public static final BigInteger two=new BigInteger("2");
	public static final BigInteger three=new BigInteger("3");
	public static final long x=(long)(Math.pow(2, 16)-17);
	public static final BigInteger p1=BigInteger.valueOf(x);
	public static final BigInteger zero=BigInteger.ZERO;
	public static final BigInteger d1=new BigInteger("154");
	public static final BigInteger n1=new BigInteger("16339");
	public static final int N= 1000;
	public static void main(String[] args){
	    BigInteger [] a1=new BigInteger[2];
	    long value;
		a1[0]=new BigInteger("12");
		a1[1]=new BigInteger("61833");
		value=check(a1,d1,p1,n1);
        System.out.println(value);
    }
    public static BigInteger[] mul(BigInteger[] a1, BigInteger[] a2, BigInteger d, BigInteger p) {
    	BigInteger [] temp = new BigInteger[3];
    	BigInteger [] a3 = new BigInteger[2];
    	temp[0]=a1[0].multiply(a2[1]).mod(p);
    	temp[1]=a2[0].multiply(a1[1]).mod(p);
    	temp[2]=a1[0].multiply(a2[0]).multiply(a1[1]).multiply(a2[1]).multiply(d);
    	temp[0]=temp[0].add(temp[1]).mod(p);
    	temp[1]=temp[2].add(one);
    	a3[0]=temp[0].multiply(temp[1].modInverse(p)).mod(p);
    	temp[0]=a1[1].multiply(a2[1]).mod(p);
    	temp[1]=a1[0].multiply(a2[0]).mod(p);
    	temp[0]=temp[0].subtract(temp[1]).mod(p);
    	temp[1]=one.subtract(temp[2]);
    	a3[1]=temp[0].multiply(temp[1].modInverse(p)).mod(p);
    	return a3;
    }
    public static BigInteger[] exp(BigInteger[] a, BigInteger m, BigInteger d, BigInteger p) {
    	BigInteger [] result= a;
    	int i=m.bitLength();
    	for(i=m.bitLength();i>=0;i--) {
    		result=mul(result,result,d,p);
    		if(m.testBit(i)) {
    			result=mul(result,a,d,p);
    		}
    	}
    	return result;
    }
    public static BigInteger[] rho(BigInteger[] a, BigInteger[] b, BigInteger d, BigInteger p, BigInteger n) {
    	BigInteger []zk=new BigInteger[2]; 
    	zk[0]=zero;
    	zk[1]=one;
    	BigInteger []z2k=new BigInteger[2];
    	z2k=zk;
    	BigInteger ak = zero;
    	BigInteger bk = zero;
    	BigInteger a2k = zero;
    	BigInteger b2k = zero;
    	BigInteger mu = zero;
    	BigInteger []result=new BigInteger[2];
    	BigInteger k= zero;
    	while (true) {
    		switch(zk[0].mod(three).compareTo(one)) {
        	case -1:
        		zk=mul(zk,b,d,p);
        		ak=ak.add(one).mod(n);
        	case 0:
        		zk=mul(zk,zk,d,p);
        		ak=ak.multiply(two).mod(n);
        		bk=bk.multiply(two).mod(n);
        	case 1:
        		zk=mul(zk,a,d,p);
        		bk=bk.add(one).mod(n);		
        	}
    		switch(z2k[0].mod(three).compareTo(one)) {
        	case -1:
        		z2k=mul(z2k,b,d,p);
        		ak=ak.add(one).mod(n);
        	case 0:
        		z2k=mul(z2k,z2k,d,p);
        		ak=ak.multiply(two).mod(n);
        		bk=bk.multiply(two).mod(n);
        	case 1:
        		z2k=mul(z2k,a,d,p);
        		bk=bk.add(one).mod(n);	
        	}
    		switch(z2k[0].mod(three).compareTo(one)) {
        	case -1:
        		z2k=mul(z2k,b,d,p);
        		ak=ak.add(one).mod(n);
        	case 0:
        		z2k=mul(z2k,z2k,d,p);
        		ak=ak.multiply(two).mod(n);
        		bk=bk.multiply(two).mod(n);
        	case 1:
        		z2k=mul(z2k,a,d,p);
        		bk=bk.add(one).mod(n);		
        	}
    		k=k.add(one);
    		if (z2k[0].compareTo(zk[0])==0&&z2k[1].compareTo(zk[1])==0) {
    			mu=ak.subtract(a2k);
    			if(mu.mod(n).compareTo(zero)==0) {
    				return null;
    			}
    			else {
    				result[0]=b2k.subtract(bk).multiply(mu.modInverse(n)).mod(n);
    				result[1]=k;
    				return result;
    			}
    		}
    	}
    	
    }
    public static long check(BigInteger[] a, BigInteger d, BigInteger p, BigInteger n) {
    	int times=N;
    	int i,nint;
    	long average;
    	nint=n.intValue();
    	nint=(int) (Math.log1p(nint)/Math.log(2));
    	Random random=new Random();
    	BigInteger m=zero,sum=zero;
    	BigInteger []b=new BigInteger[2]; 
    	BigInteger []result=new BigInteger[2]; 
    	for (i=0;i<N;i++) {
    		m=new BigInteger(nint, random);
    		b=exp(a,m,d,p);
    		result=rho(a,b,d,p,n);
    		if (result==null||result[0].compareTo(m)==0) {
    			times=times-1;
    		}
    		else {
    			sum=sum.add(result[1]);
    		}
    	}
    	average=sum.longValue()/times;
    	return average;
    }
    
}