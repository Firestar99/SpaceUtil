package space.util.number.integer.nativeType;

import space.util.math.MathUtils;
import space.util.number.base.NumberMulDiv;
import space.util.number.integer.IInteger;

public class NumberByte extends IInteger<NumberByte> implements NumberMulDiv<NumberByte> {
	
	public byte b;
	
	public NumberByte() {
	}
	
	public NumberByte(byte b) {
		this.b = b;
	}
	
	@Override
	public NumberByte set(NumberByte n) {
		b = n.b;
		return this;
	}
	
	@Override
	public NumberByte make() {
		return new NumberByte();
	}
	
	@Override
	public NumberByte copy() {
		return new NumberByte(b);
	}
	
	//methods with Wrapper
	@Override
	public NumberByte add(NumberByte numberByte) {
		return add(numberByte.b);
	}
	
	@Override
	public NumberByte sub(NumberByte numberByte) {
		return sub(numberByte.b);
	}
	
	@Override
	public NumberByte add(NumberByte numberByte, int[] overflow) {
		return add(numberByte.b, overflow);
	}
	
	@Override
	public NumberByte sub(NumberByte numberByte, int[] overflow) {
		return sub(numberByte.b, overflow);
	}
	
	@Override
	public NumberByte negate() {
		b ^= 0x80;
		return this;
	}
	
	@Override
	public NumberByte multiply(NumberByte numberByte) {
		return multiply(numberByte.b);
	}
	
	@Override
	public NumberByte divide(NumberByte numberByte) {
		return divide(numberByte.b);
	}
	
	@Override
	public NumberByte pow2() {
		b <<= 2;
		return this;
	}
	
	@Override
	public NumberByte pow(NumberByte pow) {
		return pow(pow.b);
	}
	
	@Override
	public NumberByte shiftRight(NumberByte shift) {
		return shiftRight(shift.b);
	}
	
	@Override
	public NumberByte shiftRightLog(NumberByte shift) {
		return shiftRightLog(shift.b);
	}
	
	@Override
	public NumberByte shiftLeft(NumberByte shift) {
		return shiftLeft(shift.b);
	}
	
	//methods with primitive
	public NumberByte add(byte n) {
		b += n;
		return this;
	}
	
	public NumberByte sub(byte n) {
		b -= n;
		return this;
	}
	
	public NumberByte add(byte n, int[] overflow) {
		int i = (int) b + n;
		b = (byte) i;
		overflow[0] += i >> 8;
		return this;
	}
	
	public NumberByte sub(byte n, int[] overflow) {
		int i = (int) b - n;
		b = (byte) i;
		overflow[0] += i >> 8;
		return this;
	}
	
	public NumberByte multiply(byte n) {
		b *= n;
		return this;
	}
	
	public NumberByte divide(byte n) {
		b /= n;
		return this;
	}
	
	public NumberByte pow(byte pow) {
		b = MathUtils.pow(b, pow);
		return this;
	}
	
	public NumberByte shiftRight(byte shift) {
		b >>= shift;
		return this;
	}
	
	public NumberByte shiftRightLog(byte shift) {
		b >>>= shift;
		return this;
	}
	
	public NumberByte shiftLeft(byte shift) {
		b <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return Byte.toString(b);
	}
}
