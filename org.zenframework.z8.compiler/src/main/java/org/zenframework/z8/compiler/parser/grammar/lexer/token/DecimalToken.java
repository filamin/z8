package org.zenframework.z8.compiler.parser.grammar.lexer.token;

import java.math.BigDecimal;

import org.zenframework.z8.compiler.core.IPosition;
import org.zenframework.z8.compiler.parser.type.Primary;

public class DecimalToken extends ConstantToken {
	private BigDecimal value;

	public DecimalToken() {
	}

	public DecimalToken(BigDecimal value, IPosition position) {
		super(position);
		this.value = value;
	}

	public BigDecimal getValue() {
		return value;
	}

	@Override
	public String format(boolean forCodeGeneration) {
		return value.toString();
	}

	@Override
	public String getTypeName() {
		return Primary.Decimal;
	}

	@Override
	public String getSqlTypeName() {
		return Primary.SqlDecimal;
	}
}
