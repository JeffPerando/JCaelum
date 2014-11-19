
package com.elusivehawk.caelum.input;

import static com.elusivehawk.caelum.input.EnumKeyType.FUNCTIONAL;
import static com.elusivehawk.caelum.input.EnumKeyType.F_KEY;
import static com.elusivehawk.caelum.input.EnumKeyType.LETTER;
import static com.elusivehawk.caelum.input.EnumKeyType.MISC;
import static com.elusivehawk.caelum.input.EnumKeyType.NUMBER;
import static com.elusivehawk.caelum.input.EnumKeyType.SYMBOL;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum Key
{
	A(LETTER, 'a'),
	B(LETTER, 'b'),
	C(LETTER, 'c'),
	D(LETTER, 'd'),
	E(LETTER, 'e'),
	F(LETTER, 'f'),
	G(LETTER, 'g'),
	H(LETTER, 'h'),
	I(LETTER, 'i'),
	J(LETTER, 'j'),
	K(LETTER, 'k'),
	L(LETTER, 'l'),
	M(LETTER, 'm'),
	N(LETTER, 'n'),
	O(LETTER, 'o'),
	P(LETTER, 'p'),
	Q(LETTER, 'q'),
	R(LETTER, 'r'),
	S(LETTER, 's'),
	T(LETTER, 't'),
	U(LETTER, 'u'),
	V(LETTER, 'v'),
	W(LETTER, 'w'),
	X(LETTER, 'x'),
	Y(LETTER, 'y'),
	Z(LETTER, 'z'),
	
	NUM_1(NUMBER, '1', '!'),
	NUM_2(NUMBER, '2', '@'),
	NUM_3(NUMBER, '3', '#'),
	NUM_4(NUMBER, '4', '$'),
	NUM_5(NUMBER, '5', '%'),
	NUM_6(NUMBER, '6', '^'),
	NUM_7(NUMBER, '7', '&'),
	NUM_8(NUMBER, '8', '*'),
	NUM_9(NUMBER, '9', '('),
	NUM_0(NUMBER, '0', ')'),
	
	ALT(FUNCTIONAL),
	BACKSPACE(FUNCTIONAL),
	CONTROL(FUNCTIONAL),
	DELETE(FUNCTIONAL),
	END(FUNCTIONAL),
	ESCAPE(FUNCTIONAL),
	FUNC(FUNCTIONAL),
	HOME(FUNCTIONAL),
	INSERT(FUNCTIONAL),
	MENU(FUNCTIONAL),
	PAGE_DOWN(FUNCTIONAL),
	PAGE_UP(FUNCTIONAL),
	SHIFT(FUNCTIONAL),
	START(FUNCTIONAL),
	
	F1(F_KEY),
	F2(F_KEY),
	F3(F_KEY),
	F4(F_KEY),
	F5(F_KEY),
	F6(F_KEY),
	F7(F_KEY),
	F8(F_KEY),
	F9(F_KEY),
	F10(F_KEY),
	F11(F_KEY),
	F12(F_KEY),
	F13(F_KEY),
	F14(F_KEY),
	F15(F_KEY),
	F16(F_KEY),
	F17(F_KEY),
	F18(F_KEY),
	F19(F_KEY),
	
	BACKSLASH(SYMBOL, '\\', '|'),
	BRACKET_LEFT(SYMBOL, '[', '{'),
	BRACKET_RIGHT(SYMBOL, ']', '}'),
	COMMA(SYMBOL, ',', '<'),
	DASH(SYMBOL, '-', '_'),
	EQUALS(SYMBOL, '=', '+'),
	GRAVE(SYMBOL, '`', '~'),
	PERIOD(SYMBOL, '.', '>'),
	SEMICOLON(SYMBOL, ';', ':'),
	SLASH(SYMBOL, '/', '?'),
	
	ARROW_DOWN(MISC),
	ARROW_LEFT(MISC),
	ARROW_RIGHT(MISC),
	ARROW_UP(MISC),
	CAPS_LOCK(MISC, true),
	ENTER(MISC, '\n'),
	NUM_LOCK(MISC, true),
	SCROLL_LOCK(MISC, true),
	SPACE(MISC, ' '),
	TAB(MISC, '\t'),
	
	UNKNOWN(MISC);
	
	public final EnumKeyType keyType;
	
	private final Character lower, upper;
	private final boolean isLock;
	
	Key(EnumKeyType type)
	{
		this(type, null, null);
	}
	
	Key(EnumKeyType type, Character ch)
	{
		this(type, ch, Character.toUpperCase(ch));
		
	}
	
	Key(EnumKeyType type, boolean lock)
	{
		this(type, null, null, lock);
		
	}
	
	Key(EnumKeyType type, Character lcase, Character ucase)
	{
		this(type, lcase, ucase, false);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	Key(EnumKeyType type, Character lcase, Character ucase, boolean lock)
	{
		keyType = type;
		lower = lcase;
		upper = ucase;
		isLock = lock;
		
	}
	
	public Character getChar(boolean caps)
	{
		return caps ? this.upper : this.lower;
	}
	
	public boolean isLock()
	{
		return this.isLock;
	}
	
}
