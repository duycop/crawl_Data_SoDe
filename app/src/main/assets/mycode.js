$(document).ready(function(){
    var timer;
    function update_time(){
      var json_str = window.mo_rong.get_sode(); //lấy giá trị biến dem trong java
      //$('#test').html(json_str);
      var json= JSON.parse(json_str);
      $('#ngay').html(json.ngay);
      if(json.giai_db!='Loading...')
      {
        var sode=json.giai_db.slice(-2);
        $('#kq').html(sode);
        //ko can goi nua: clear timer
        //clearInterval(timer)
      }else{
        $('#kq').html(json.giai_db);
      }
  }
  function get_thoitiet(tp){
        $('#test').html("chuẩn bị lấy info thời tiết tp: "+tp);
        var settings = {
                "url": "https://tms.tnut.edu.vn/55kmt/thoitiet.php?tp="+tp,
                "method": "GET",
              };
        $.ajax(settings)
          .done(function (response) {
             //$('#test').html("ok: "+response);
             var json=JSON.parse(response)[0]
             var s=`<div class="bm_Jw"><div class="bm_Jx"><figure class="bm_Bh"><img src="`+json.CA+`" alt=""></figure><div class="bm_Gg"><span class="bm_Gh">`+json.CT+`º</span><span class="bm_GM">`+json.MinT+`º - `+json.MaxT+`º</span></div>
                    <div class="bm_Gi"><span>`+json.I+`</span><span>Độ ẩm: `+json.CH+`%</span><span>Gió: `+json.CW+`km/h</span><span>Khả năng mưa: `+json.CP+`%</span></div>`;
             for(var i=1;i<=4;i++)
             s+=`
             <li>
                 <div class="bm_GG">28/12</div>
                 <div class="bm_Jg">
                     <figure class="bm_Bh">
                         <img src="`+json['A'+i]+`" alt="">
                     </figure>
                 </div>
                 <div class="bm_GM">`+json['MinT'+i]+`º - `+json['MaxT'+i]+`º</div>
                 <div class="bm_Jy">`+json['I'+i]+`</div>
             </li>
             `;
             $('#chitiet').html(s)
          })
          .fail(function (jqXHR, textStatus) {
              $('#test').html("Error: "+textStatus);
          });
  }
  function get_tp(){
      $('#test').html("chuan bi lay ds tp");
      var settings = {
        "url": "https://tms.tnut.edu.vn/55kmt/dstp.php",
        "method": "GET",
      };

      $.ajax(settings)
        .done(function (response) {
            var json=JSON.parse(response)
            var s='';
            for(var x of json){
                s+= '<option value="'+x.ShortName+'">'+x.CityName+'</option>';
            }
            $('#tp').html(s);
            get_thoitiet(json[0].ShortName);
        })
        .fail(function (jqXHR, textStatus) {
            $('#test').html("Error: "+textStatus);
        });
  }
  get_tp();
  //đăng ký khi select id=tp được chọn tp khác thì gọi hàm nào đó
  $("#tp").change(function(){
    var tp=$(this).val();
    get_thoitiet(tp);
  });
  timer = setInterval(function(){update_time();},1000); //mỗi 1000ms
});