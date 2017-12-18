package space.util.number.integer.nativeType;

import space.util.number.base.NumberMulDiv;
import space.util.number.integer.IInteger;

public class NumberInt extends IInteger<NumberInt> implements NumberMulDiv<NumberInt> {
	
	public int i;
	
	public NumberInt() {
	}
	
	public NumberInt(int i) {
		this.i = i;
	}
	
	@Override
	public NumberInt set(NumberInt n) {
		i = n.i;
		return this;
	}
	
	@Override
	public NumberInt make() {
		return new NumberInt();
	}
	
	@Override
	public NumberInt copy() {
		return new NumberInt(i);
	}
	
	//methods with Wrapper
	@Override
	public NumberInt add(NumberInt numberByte) {
		return add(numberByte.i);
	}
	
	@Override
	public NumberInt sub(NumberInt numberByte) {
		return sub(numberByte.i);
	}
	
	@Override
	public NumberInt add(NumberInt numberByte, int[] overflow) {
		return add(numberByte.i, overflow);
	}
	
	@Override
	public NumberInt sub(NumberInt numberByte, int[] overflow) {
		return sub(numberByte.i, overflow);
	}
	
	@Override
	public NumberInt negate() {
		i ^= 0x80000000;
		return this;
	}
	
	@Override
	public NumberInt multiply(NumberInt numberByte) {
		return multiply(numberByte.i);
	}
	
	@Override
	public NumberInt divide(NumberInt numberByte) {
		return divide(numberByte.i);
	}
	
	@Override
	public NumberInt pow2() {
		i <<= 2;
		return this;
	}
	
	@Override
	public NumberInt pow(NumberInt pow) {
		return pow(pow.i);
	}
	
	@Override
	public NumberInt shiftRight(NumberInt shift) {
		return shiftRight(shift.i);
	}
	
	@Override
	public NumberInt shiftRightLog(NumberInt shift) {
		return shiftRightLog(shift.i);
	}
	
	@Override
	public NumberInt shiftLeft(NumberInt shift) {
		return shiftLeft(shift.i);
	}
	
	//methods with primitive
	public NumberInt add(int n) {
		i += n;
		return this;
	}
	
	public NumberInt sub(int n) {
		i -= n;
		return this;
	}
	
	public NumberInt add(int n, int[] overflow) {
		long i = this.i + n;
		this.i = (int) i;
		overflow[0] += i >> 32;
		return this;
	}
	
	public NumberInt sub(int n, int[] overflow) {
		long i = this.i - n;
		this.i = (int) i;
		overflow[0] += i >> 32;
		return this;
	}
	
	public NumberInt multiply(int n) {
		i *= n;
		return this;
	}
	
	public NumberInt divide(int n) {
		i /= n;
		return this;
	}
	
	public NumberInt pow(int pow) {
		i = (int) Math.pow(i, pow);
		return this;
	}
	
	public NumberInt shiftRight(int shift) {
		i >>= shift;
		return this;
	}
	
	public NumberInt shiftRightLog(int shift) {
		i >>>= shift;
		return this;
	}
	
	public NumberInt shiftLeft(int shift) {
		i <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return Integer.toString(i);
	}
}
