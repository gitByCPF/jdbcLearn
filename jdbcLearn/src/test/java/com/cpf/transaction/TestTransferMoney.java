package com.cpf.transaction;

import org.junit.Test;

/**
 * @author CPF 创建于： 2021/8/15 1:28
 * @version 1.0
 */
public class TestTransferMoney{
	@Test
	public void testCreateTable() throws Exception{
		TransferMoney tf = new TransferMoney();
		tf.createTable();
	}

	@Test
	public void testOpenAccount() throws Exception{
		TransferMoney tf = new TransferMoney();
		tf.openAccount();
	}

	@Test
	public void testTransfer() throws Exception{
		TransferMoney tf = new TransferMoney();
		tf.transfer();
	}
}
