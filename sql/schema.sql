CREATE TABLE public.prog_mst (
	prog_id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	prog_nm varchar(50) NULL,
	prog_desc text NULL,
	view_attr jsonb NULL,
	use_yn bool NOT NULL DEFAULT false,
	crtd_dttm timestamp(0) NULL,
	updt_dttm timestamp(0) NULL,
	dlt_dttm timestamp(0) NULL,
	CONSTRAINT pk_prog_mst PRIMARY KEY (prog_id)
);

CREATE TABLE public.prog_work_flow_mng (
	flow_id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	prog_id int8 NOT NULL,
	flow_seq int4 NOT NULL,
	flow_type varchar(5) NOT NULL,
	flow_attr jsonb NULL,
	flow_desc text NULL,
	crtd_dttm timestamp(0) NULL,
	updt_dttm timestamp(0) NULL,
	dlt_dttm timestamp(0) NULL,
	CONSTRAINT pk_prog_work_flow_mng PRIMARY KEY (flow_id),
	CONSTRAINT fk_prog_work_flow_mng FOREIGN KEY (prog_id) REFERENCES public.prog_mst(prog_id) ON DELETE CASCADE
);

CREATE TABLE public.online_trans_isol (
	idx int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	user_no numeric(13) NOT NULL,
	user_id varchar(20) NOT NULL,
	yyyymmdd varchar(8) NOT NULL,
	hhmissff varchar(6) NOT NULL,
	out_pay_bcd varchar(3) NULL,
	out_pay_acc varchar(40) NULL,
	out_pay_name varchar(30) NULL,
	in_pay_bcd varchar(3) NULL,
	in_pay_acc varchar(40) NULL,
	in_pay_name varchar(30) NULL,
	tot_amt numeric(38) NULL,
	balance numeric(38) NULL,
	acct_desc varchar(255) NULL,
	acct_type varchar(2) NULL,
	user_type varchar(2) NULL,
	device_info text NULL,
	inpt_dttm timestamp(0) NULL,
	CONSTRAINT pk_online_trans_isol PRIMARY KEY (idx)
);
