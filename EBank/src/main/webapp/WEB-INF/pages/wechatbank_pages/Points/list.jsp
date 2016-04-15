<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<%@include file="../../base_pages/wxjs.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/wechatbank/index.css"/>" />
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>

<body>
	<header>
		<span></span>
		<h2>我的积分</h2>
		<span></span>
	</header>
	<div class="mg-tb-20">
		<!-- 不存在积分 -->
		<c:if test="${fn:length(newList) eq 0}">
			<div class="topOneB mar-1 allOneBradius">
				<table class="topTwo mb-10">
					<tr>
						<td><span class="">很抱歉！没有查询到您的信用卡积分。详情请咨询中国银行信用卡24小时客服热线4006695566。感谢您对中行信用卡的支持和厚爱！</span></td>
					</tr>
				</table>
			</div>
		</c:if>
		<!-- 存在积分 -->
		<c:if test="${fn:length(newList) gt 0}">
			<c:forEach items="${newList}" var="pointsDetailList"
				varStatus="statusParent">
				<!-- 不存在子积分的积分明细 -->
				<c:if test="${fn:length(pointsDetailList) eq 1}">
					<c:forEach items="${pointsDetailList}" var="item" varStatus="status">
						<div class="topOneB mar-1 topOneBradius bcS">
							<table class="topTwo">
								<tr>
									<td><span class="">产品名称：</span></td>
									<td><c:out value="${item.productName}" /></td>
								</tr>
							</table>
						</div>
						<div class="topOneB mar-1 bcS">
							<table class="topTwo">
								<tr>
									<td><span class="">有效积分总额：</span></td>
									<td><c:out value="${item.totalPoint}" /></td>
								</tr>
							</table>
						</div>
						<div style="margin-top: 25px;"></div>
					</c:forEach>
				</c:if>
				<!-- 存在子积分的积分明细 -->
				<c:if test="${fn:length(pointsDetailList) gt 1}">
					<c:forEach items="${pointsDetailList}" var="item" varStatus="status">
						<c:if test="${fn:length(item.parentId) gt 0}">
							<div class="topOneB mar-1 topOneBradius">
								<table class="topTwo">
									<tr>
										<td><span class="">
										信用卡号：<c:out value="${fn:substring(item.cardNo,0,4)}" />********<c:out value="${fn:substring(item.cardNo,fn:length(item.cardNo)-4,fn:length(item.cardNo))}" /></span></td>
									</tr>
								</table>
							</div>
							<div class="topOneB mar-1">
								<table class="topTwo">
									<tr>
										<td><span class="">产品名称：<c:out
													value="${item.productName}" /></span></td>
									</tr>
								</table>
							</div>
							<div class="topOneB mar-1 ">
								<table class="topTwo">
									<tr>
										<td><span class="">积分余额：<c:out
													value="${item.totalPoint}" /></span></td>
									</tr>
								</table>
							</div>
							<c:if test="${item.pointuseFlg eq '正常'}">
								<div class="topOneB mar-1">
									<table class="topTwo">
										<tr>
											<td><span class="">积分账户状态：
												<c:out value="${item.pointuseFlg}" />
											</span></td>

										</tr>
									</table>
								</div>
								<div class="topOneB mar-1 bottomOneBradius">
									<table class="topTwo">
										<tr>
											<td><a class="allA"
												href="../points/validate.do?numberP=${statusParent.index}&number=${status.index}&">积分到期日</a>
											</td>
										</tr>
									</table>
								</div>
							</c:if>
							<c:if test="${item.pointuseFlg eq '冻结'}">
								<div class="topOneB mar-1 bottomOneBradius">
									<table class="topTwo">
										<tr>
											<td><span class="">积分账户状态：  <c:out value="${item.pointuseFlg}" />
											</span></td>
										</tr>
									</table>
								</div>
							</c:if>
						</c:if>

						<c:if test="${fn:length(item.parentId) eq 0}">
							<div class="topOneB mar-1 topOneBradius bcS">
								<table class="topTwo">
									<tr>
										<td><span class="">产品名称：</span></td>
										<td><c:out value="${item.productName}" /></td>
									</tr>
								</table>
							</div>
							<div class="topOneB mar-1 bottomOneBradius bcS">
								<table class="topTwo">
									<tr>
										<td><span class="">有效积分总额：</span></td>
										<td><c:out value="${item.totalPoint}" /></td>
									</tr>
								</table>
							</div>
						</c:if>
					</c:forEach>
				</c:if>
				<div style="margin-top: 25px;"></div>
			</c:forEach>
		</c:if>
	</div>
</body>
</html>