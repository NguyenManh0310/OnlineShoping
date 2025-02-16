<%@include file="template/header.jsp" %>
<div id="content-left">
    <ul>
        <a href="${pageContext.request.contextPath}/admin?p=dashboard"><li style="background-color: sienna;color: white">
                Dashboard</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=orders&page=1"><li>Orders</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=products&page=1"><li>Products</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=customer"><li>Customers</li></a>
    </ul>
</div>
<div id="content-right">
    <div class="path-admin">DASHBOARD</b></div>
    <div class="content-main">
        <div id="content-main-dashboard">
            <div id="dashboard-1">
                <div id="dashboard-1-container">
                    <div class="dashboard-item">
                        <div class="dashboard-item-title">Weekly Sales</div>
                        <div class="dashboard-item-content">$47K</div>
                    </div>
                    <div class="dashboard-item">
                        <div class="dashboard-item-title">Total Orders</div>
                        <div class="dashboard-item-content">$1000K</div>
                    </div>
                    <div class="dashboard-item">
                        <div class="dashboard-item-title">Total Customers</div>
                        <div class="dashboard-item-content">5K</div>
                    </div>
                    <div class="dashboard-item">
                        <div class="dashboard-item-title">Total Guest</div>
                        <div class="dashboard-item-content">3K</div>
                    </div>
                </div>
            </div>
            <div id="dashboard-2">
                <div id="chart" style="text-align: center;">
                    <div id="chart1">
                        <h3>Statistic Orders (Month)</h3>
                        <canvas id="myChart1" style="width: 100%;"></canvas>
                    </div>
                    <div id="chart2">
                        <canvas id="myChart2" style="width: 80%;"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="template/footer.jsp" %>
<script>
    function OrdersChart(){
        var xValues = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,
        19,20,21,22,23,24,25,26,27,28,29,30,31];
        
    
        new Chart("myChart1", {
        type: "line",
        data: {
            labels: xValues,
            datasets: [{ 
            data: [1600,1700,1700,1900,2000,2700,4000,5000,6000,7000,8000,8100],
            borderColor: "sienna",
            fill: true
            }]
        },
        options: {
            legend: {display: false}
        }
        });
    }

    function CustomersChart(){
        var xValues = ["Total", "New customer"];
        var yValues = [200, 50,300];
        var barColors = ["green", "red"];

        new Chart("myChart2", {
        type: "bar",
        data: {
            labels: xValues,
            datasets: [{
            backgroundColor: barColors,
            data: yValues
            }]
        },
        options: {
            legend: {display: false},
            title: {
            display: true,
            text: "New Customers (30 daily Avg)"
            }
        }
        });
    }
    
    OrdersChart();
    CustomersChart();
    </script>
