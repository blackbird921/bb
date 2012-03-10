<!DOCTYPE html>
<html>
<head>
    <title></title>
    <script src="/scripts/kendo.all.js"></script>
    <link href="/styles/kendo.common.css" rel="stylesheet" />
    <link href="/styles/kendo.default.css" rel="stylesheet" />
</head>
<body>
                                                       xxxx
                                                       <a href="../index.html">Back</a>
                                                       <div class="description">Basic usage</div>
                                                       <div id="example" class="k-content">
                                                           <div id="background">
                                                               <div id="calendar"></div>
                                                           </div>
                                                           <script>
                                                               $(document).ready(function() {
                                                                   // create Calendar from div HTML element
                                                                   $("#calendar").kendoCalendar();
                                                               });
                                                           </script>
                                                           <style scoped>
                                                               #background {
                                                                   width: 254px;
                                                                   height: 250px;
                                                                   margin: 30px auto;
                                                                   padding: 69px 0 0 11px;
                                                                   background: url('../content/calendar/calendar.png') transparent no-repeat 0 0;
                                                               }
                                                               #calendar {
                                                                   width: 241px;
                                                               }
                                                           </style>
                                                       </div>

</body>
</html>