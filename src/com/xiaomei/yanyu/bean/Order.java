package com.xiaomei.yanyu.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.util.DateUtils;

/*
{
    "data_detail": {
        "goods_info": {
            "order_amount": "6000",
            "goods_name": "纤纤玉腿的秘密-美国进口Botox",
            "goods_img": "http://bcs.duapp.com/drxiaomei/images/goods_common/20150409103832_25154.jpg"
        },
        "order_info": [
            {
                "value": "201504111313185450",
                "title": "订单号"
            },
            {
                "value": "1428729198",
                "title": "下单日期"
            },
            {
                "value": "两个月内均可体验",
                "title": "服务日期"
            },
            {
                "value": "未定义",
                "title": "客户姓名"
            },
            {
                "value": "15010768102",
                "title": "客户电话"
            },
            {
                "value": "123",
                "title": "护照号"
            }
        ],
        "hosp_info": {
            "hosp_name": "德碧亶the Bidan整形外科",
            "addr": "韩国首尔市江南区新沙洞642-26",
            "tel": "+82-2-543-4364"
        }
    },
    "data_list": {
        "id": "19062291",
        "order_amount": "6000",
        "username": "未定义",
        "status": "1",
        "order_num": "201504111313185450",
        "goods_id": "1015",
        "createdate": "1428729198",
        "goods_name": "纤纤玉腿的秘密-美国进口Botox",
        "hosp_name": "德碧亶the Bidan整形外科",
        "goods_img": "http://bcs.duapp.com/drxiaomei/images/goods_common/20150409103832_25154.jpg",
        "city": "首尔"
    }
}
*/
public class Order implements Serializable{
	
	
	private DataList data_list;
	
	private DataDetail data_detail;
	
	public DataList getDataList() {
		return data_list;
	}

	public void setDataList(DataList dataList) {
		this.data_list = dataList;
	}

	public DataDetail getDataDetail() {
		return data_detail;
	}

	public void setDataDetail(DataDetail dataDetail) {
		this.data_detail = dataDetail;
	}

	@Override
	public String toString() {
		return "Order [dataList=" + data_list + ", dataDetail=" + data_detail
				+ "]";
	}



	/**订单详情信息*/
	public static class DataDetail implements Serializable{
		
		private GoodsInfo goods_info;

        private List<OrderInfo> order_info;

        private HospInfo hosp_info;
		
		public GoodsInfo getGoodsInfo() {
			return goods_info;
		}

		public void setGoodsInfo(GoodsInfo goodsInfo) {
			this.goods_info = goodsInfo;
		}

		public List<OrderInfo> getOrderInfos() {
            return order_info;
		}

		public void setOrderInfos(List<OrderInfo> orderInfos) {
            this.order_info = orderInfos;
		}

		public HospInfo getHospInfo() {
            return hosp_info;
		}

		public void setHospInfo(HospInfo hospInfo) {
            this.hosp_info = hospInfo;
		}

		public static class GoodsInfo implements Serializable{
            private String goods_pay;

            private String goods_name;

            private String goods_img;
			public String getOrderAmount() {
                return goods_pay;
			}
			public void setOrderAmount(String orderAmount) {
                this.goods_pay = orderAmount;
			}
			public String getGoodsName() {
                return goods_name;
			}
			public void setGoodsName(String goodsName) {
                this.goods_name = goodsName;
			}
			public String getGoodsImg() {
                return goods_img;
			}
			public void setGoodsImg(String goodsImg) {
                this.goods_img = goodsImg;
			}
			@Override
			public String toString() {
                return "GoodsInfo [orderAmount=" + goods_pay + ", goodsName=" + goods_name
                        + ", goodsImg=" + goods_img + "]";
			}
			
			
		}
		
		public static class OrderInfo implements Serializable{

            public static final Type[] sTypes = new Type[] {
                    Type.ORDER_ID,
                    Type.PRESERVE_DATE,
                    Type.USER_NAME,
                    Type.MOBILE,
                    Type.PASSPORT,
                    Type.COUPON_ID,
                    Type.PAYMENT_DATE,
            };

            private String value;
			private String title;
            private Type type;

			public String getValue() {
				return value;
			}
			public void setValue(String value) {
				this.value = value;
			}
            public String getDisplayValue() {
                if (type == Type.PRESERVE_DATE || type == Type.PAYMENT_DATE) {
                    return (value != null && !value.isEmpty()) ?
                            DateUtils.formateDate(value) : value;
                }
                return "0".equals(value) ? null : value;
            }
			public String getTitle() {
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
			}

            public Type getType() {
                if (type == null) {
                    for (Type t : sTypes) {
                        if (t.title.equals(title)) {
                            type = t;
                            break;
                        }
                    }
                }
                return type;
            }

			@Override
			public String toString() {
				return "OrderInfo [value=" + value + ", title=" + title + "]";
			}
			
			public static enum Type {
			    ORDER_ID(R.id.preference_order_id, "订单号", false),
			    PRESERVE_DATE(R.id.preference_preserve_date, "预约日期", false),
			    USER_NAME(R.id.preference_user_name, "客户姓名", R.string.preference_user_name_hint, true),
			    MOBILE(R.id.preference_mobile, "客户电话", R.string.preference_mobile_hint, true),
			    PASSPORT(R.id.preference_passport, "护照号", R.string.preference_passport_hint, true),
			    COUPON_ID(R.id.preference_coupon_id, "优惠折扣", false),
			    PAYMENT_DATE(R.id.preference_payment_date, "支付日期", false);

                public final int id;
                public final String title;
                public final int hintRes;
			    public final boolean editable;
			    
                Type(int id, String title, boolean editable) {
                    this(id, title, 0, editable);
			    }
                
                Type(int id, String title, int hintRes, boolean editable) {
                    this.id = id;
                    this.title = title;
                    this.hintRes = hintRes;
                    this.editable = editable;
                }
			}
		}
		
		public static class HospInfo implements Serializable{
            private String hosp_name;
            private String addr;
            private String tel;
			public String getHospName() {
                return hosp_name;
			}
			public void setHospName(String hospName) {
                this.hosp_name = hospName;
			}
			public String getAddr() {
				return addr;
			}
			public void setAddr(String addr) {
				this.addr = addr;
			}
			public String getTel() {
				return tel;
			}
			public void setTel(String tel) {
				this.tel = tel;
			}
			@Override
			public String toString() {
                return "HospInfo [hospName=" + hosp_name + ", addr=" + addr
						+ ", tel=" + tel + "]";
			}
		}

		@Override
		public String toString() {
			return "DataDetail [goodsInfo=" + goods_info + ", orderInfos="
 + order_info
                    + ", hospInfo=" + hosp_info + "]";
		}

        public OrderInfo findOrderInfo(OrderInfo.Type type) {
            for (OrderInfo orderInfo : order_info) {
                if (orderInfo.type == type) {
                    return orderInfo;
                }
            }
            return null;
        }
		
		
	}

	/**我的订单列表信息*/
	public static class DataList implements Serializable{
	    
	    private static final Map<String, String> sStatusMap = new HashMap<String, String>() {{
	        put("1", "未付款");
	        put("2", "已付款");
	        put("3", "退款审核中");
	        put("4", "已消费");
	        put("5", "交易结束");
	        put("6", "退款完成");
	    }};
	    
		private String id;
		private String orderAmount;
		private String username;
		private String status;
		private String order_num;
		private String goods_id;
		private String createdate;
		private String goods_name;
		private String hosp_name;
		private String goods_img;
		private String goods_pay;
		private String city;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getOrderAmount() {
			return orderAmount;
		}
		public void setOrderAmount(String orderAmount) {
			this.orderAmount = orderAmount;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getStatus() {
			return status;
		}
		
		public String getStatusText() {
		    return sStatusMap.get(getStatus());
		}
		
		public void setStatus(String status) {
			this.status = status;
		}
		public String getOrderNum() {
			return order_num;
		}
		public void setOrderNum(String orderNum) {
			this.order_num = orderNum;
		}
		public String getGoodsId() {
			return goods_id;
		}
		public void setGoodsId(String goodsId) {
			this.goods_id = goodsId;
		}
		public String getCreatedate() {
			return createdate;
		}
		public void setCreatedate(String createdate) {
			this.createdate = createdate;
		}
		public String getGoodsName() {
			return goods_name;
		}
		public void setGoodsName(String goodsName) {
			this.goods_name = goodsName;
		}
		public String getHospName() {
			return hosp_name;
		}
		public void setHospName(String hospName) {
			this.hosp_name = hospName;
		}
		public String getGoodsImg() {
			return goods_img;
		}
		public void setGoodsImg(String goodsImg) {
			this.goods_img = goodsImg;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getGoodsPay() {
			return goods_pay;
		}
		public void setGoodsPay(String goodsPay) {
			this.goods_pay = goodsPay;
		}
		@Override
		public String toString() {
			return "DataList [id=" + id + ", orderAmount=" + orderAmount
					+ ", username=" + username + ", status=" + status
					+ ", orderNum=" + order_num + ", goodsId=" + goods_id
					+ ", createdate=" + createdate + ", goodsName=" + goods_name
					+ ", hospName=" + hosp_name + ", goodsImg=" + goods_img
					+ ", goodsPay=" + goods_pay + ", city=" + city + "]";
		}
	}
	
	
}
