package com.xiaomei.bean;

import java.io.Serializable;
import java.util.List;

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
	
	
	private DataList dataList;
	
	private DataDetail dataDetail;
	
	public DataList getDataList() {
		return dataList;
	}

	public void setDataList(DataList dataList) {
		this.dataList = dataList;
	}

	public DataDetail getDataDetail() {
		return dataDetail;
	}

	public void setDataDetail(DataDetail dataDetail) {
		this.dataDetail = dataDetail;
	}

	@Override
	public String toString() {
		return "Order [dataList=" + dataList + ", dataDetail=" + dataDetail
				+ "]";
	}



	/**订单详情信息*/
	public static class DataDetail implements Serializable{
		
		private GoodsInfo goodsInfo;
		private List<OrderInfo> orderInfos;
		private HospInfo hospInfo;
		
		public GoodsInfo getGoodsInfo() {
			return goodsInfo;
		}

		public void setGoodsInfo(GoodsInfo goodsInfo) {
			this.goodsInfo = goodsInfo;
		}

		public List<OrderInfo> getOrderInfos() {
			return orderInfos;
		}

		public void setOrderInfos(List<OrderInfo> orderInfos) {
			this.orderInfos = orderInfos;
		}

		public HospInfo getHospInfo() {
			return hospInfo;
		}

		public void setHospInfo(HospInfo hospInfo) {
			this.hospInfo = hospInfo;
		}

		public static class GoodsInfo implements Serializable{
			private String orderAmount;
			private String goodsName;
			private String goodsImg;
			public String getOrderAmount() {
				return orderAmount;
			}
			public void setOrderAmount(String orderAmount) {
				this.orderAmount = orderAmount;
			}
			public String getGoodsName() {
				return goodsName;
			}
			public void setGoodsName(String goodsName) {
				this.goodsName = goodsName;
			}
			public String getGoodsImg() {
				return goodsImg;
			}
			public void setGoodsImg(String goodsImg) {
				this.goodsImg = goodsImg;
			}
			@Override
			public String toString() {
				return "GoodsInfo [orderAmount=" + orderAmount + ", goodsName="
						+ goodsName + ", goodsImg=" + goodsImg + "]";
			}
			
			
		}
		
		public static class OrderInfo implements Serializable{
			private String value;
			private String title;
			public String getValue() {
				return value;
			}
			public void setValue(String value) {
				this.value = value;
			}
			public String getTitle() {
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
			}
			@Override
			public String toString() {
				return "OrderInfo [value=" + value + ", title=" + title + "]";
			}
			
		}
		
		public static class HospInfo implements Serializable{
            private String hospName;
            private String addr;
            private String tel;
			public String getHospName() {
				return hospName;
			}
			public void setHospName(String hospName) {
				this.hospName = hospName;
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
				return "HospInfo [hospName=" + hospName + ", addr=" + addr
						+ ", tel=" + tel + "]";
			}
		}
	}

	/**我的订单列表信息*/
	public static class DataList implements Serializable{
		private String id;
		private String orderAmount;
		private String username;
		private String status;
		private String orderNum;
		private String goodsId;
		private String createdate;
		private String goodsName;
		private String hospName;
		private String goodsImg;
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
		public void setStatus(String status) {
			this.status = status;
		}
		public String getOrderNum() {
			return orderNum;
		}
		public void setOrderNum(String orderNum) {
			this.orderNum = orderNum;
		}
		public String getGoodsId() {
			return goodsId;
		}
		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}
		public String getCreatedate() {
			return createdate;
		}
		public void setCreatedate(String createdate) {
			this.createdate = createdate;
		}
		public String getGoodsName() {
			return goodsName;
		}
		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}
		public String getHospName() {
			return hospName;
		}
		public void setHospName(String hospName) {
			this.hospName = hospName;
		}
		public String getGoodsImg() {
			return goodsImg;
		}
		public void setGoodsImg(String goodsImg) {
			this.goodsImg = goodsImg;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		@Override
		public String toString() {
			return "DataList [id=" + id + ", orderAmount=" + orderAmount
					+ ", username=" + username + ", status=" + status
					+ ", orderNum=" + orderNum + ", goodsId=" + goodsId
					+ ", createdate=" + createdate + ", goodsName=" + goodsName
					+ ", hospName=" + hospName + ", goodsImg=" + goodsImg
					+ ", city=" + city + "]";
		}
		
		
	}
	
	
}
