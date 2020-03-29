//index 변수를 별도로 선언해 준 이유는 브라우저의 scope 이슈 때문에 그렇다.
//브라우저의 스코프는 기본적으로 공용 공간으로 쓰이기 때문에 함수 이름이 같으면 계속 덮어쓰기가 된다.
//따라서 index 객체 안에서만 scope가 돌도록 함수의 범위를 설정한 것으로 이해하면 된다.

var index = {
  init: function () {
    var _this = this;
    $('#btn-save').on('click', function () {
      _this.save();
    });

    $('#btn-update').on('click', function () {
      _this.update();
    });

    $('#btn-delete').on('click', function () {
      _this.delete();
    });
  },
  save: function () {
    var data = {
      title: $('#title').val(),
      author: $('#author').val(),
      content: $('#content').val()
    };

    $.ajax({
      type: 'POST',
      url: '/api/v1/posts',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('글이 등록되었습니다.');
      window.location.href = '/';
    }).fail(function (error) {
      alert(JSON.stringify(error));
    });
  },

  update: function () {
    var data = {
      title: $('#title').val(),
      content: $('#content').val()
    };
    var id = $('#id').val();

    $.ajax({
      type: 'PUT',
      url: '/api/v1/posts/' + id,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('글이 수정되었습니다.');
      window.location.href = '/';
    }).fail(function (error) {
      alert(JSON.stringify(error))
    });
  },
  delete: function () {
    var id = $('#id').val();
    $.ajax({
      type: 'DELETE',
      url: '/api/v1/posts/' + id,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8'
    }).done(function () {
      alert('글이 삭제되었습니다.');
      window.location.href = "/";
    }).fail(function (error) {
      alert(JSON.stringify(error))
    });
  }
};

index.init();