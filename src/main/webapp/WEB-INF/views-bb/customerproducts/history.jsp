<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/form/fields" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script type="text/javascript">

    var firstNames = ["Nancy", "Andrew", "Janet", "Margaret", "Steven", "Michael", "Robert", "Laura", "Anne", "Nige"],
            lastNames = ["Davolio", "Fuller", "Leverling", "Peacock", "Buchanan", "Suyama", "King", "Callahan", "Dodsworth", "White"],
            cities = ["Seattle", "Tacoma", "Kirkland", "Redmond", "London", "Philadelphia", "New York", "Seattle", "London", "Boston"],
            titles = ["Accountant", "Vice President, Sales", "Sales Representative", "Technical Support", "Sales Manager", "Web Designer",
                "Software Developer", "Inside Sales Coordinator", "Chief Techical Officer", "Chief Execute Officer"],
            birthDates = [new Date("1948/12/08"), new Date("1952/02/19"), new Date("1963/08/30"), new Date("1937/09/19"), new Date("1955/03/04"), new Date("1963/07/02"), new Date("1960/05/29"), new Date("1958/01/09"), new Date("1966/01/27"), new Date("1966/03/27")];

    function createRandomData(count) {
        var data = [], now = new Date();
        for (var i = 0; i < count; i++) {
            var firstName = firstNames[Math.floor(Math.random() * firstNames.length)],
                    lastName = lastNames[Math.floor(Math.random() * lastNames.length)],
                    city = cities[Math.floor(Math.random() * cities.length)],
                    title = titles[Math.floor(Math.random() * titles.length)],
                    birthDate = birthDates[Math.floor(Math.random() * birthDates.length)],
                    age = now.getFullYear() - birthDate.getFullYear();

            data.push({
                Id: i + 1,
                FirstName: firstName,
                LastName: lastName,
                City: city,
                Title: title,
                BirthDate: birthDate,
                Age: age
            });
        }
        return data;
    }


    $(document).ready(function () {
//        $(".grid").kendoGrid({
//            height:360,
//            pageSize:5,
//            groupable:true,
//            scrollable:false,
//            sortable:{
//                mode:"single",
//                allowUnsort:false
//            },
//            pageable:true
//        });
//
    });
</script>

<div id="main">
    <h1 id="exampleTitle" class="k-content">
        <span class="exampleIcon overviewIcon"></span>
        历史记录
    </h1>

    <div id="example" class="k-content" style="width:700px;">
        <h4>当前出勤计划:</h4>
        <table class="grid center-text">
            <thead>
            <tr>
                <th class="center-text">周期</th>
                <th class="center-text">承诺出勤</th>
                <th class="center-text">实际出勤</th>
                <th class="center-text">缺勤每天罚金</th>
                <th class="center-text">奖罚收入</th>
                <th class="center-text">累积收入</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>2012-01-02 ~ 2012-01-09</td>
                <td>4天</td>
                <td>2天</td>
                <td>10元</td>
                <td>-20元</td>
                <td>-84元</td>
            </tr>
            </tbody>
        </table>


        <div id="clientsDb">

            <div id="grid"></div>

        </div>

        <style scoped>
            #clientsDb {
                width: 692px;
                height: 393px;
                margin: 30px auto;
                padding: 51px 4px 0 4px;
                /*background: url('../content/grid/clientsDb.png') no-repeat 0 0;*/
            }
        </style>
        <script>
            $(document).ready(function () {
                $("#grid").kendoGrid({
                    dataSource:{
                        data:createRandomData(50),
                        pageSize:10
                    },
                    height:360,
                    groupable:true,
                    scrollable:true,
                    sortable:true,
                    pageable:true,
                    columns:[
                        {
                            field:"FirstName",
                            width:90,
                            title:"First Name"
                        } ,
                        {
                            field:"LastName",
                            width:90,
                            title:"Last Name"
                        } ,
                        {
                            width:100,
                            field:"City"
                        } ,
                        {
                            field:"Title"
                        } ,
                        {
                            field:"BirthDate",
                            title:"Birth Date",
                            template:'#= kendo.toString(BirthDate,"dd MMMM yyyy") #'
                        } ,
                        {
                            width:50,
                            field:"Age"
                        }
                    ]
                });
            });
        </script>
    </div>
</div>


