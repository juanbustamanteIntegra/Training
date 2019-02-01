function appendToDoc(persons){
  $('#thetable tr').empty();

  $('#thetable tbody tr').not(':first').not(':last').remove();
  var html = '';
  for(var i = 0; i < persons.length; i++)
  html += '<tr class="row100 body"><td class="cell100 column1">' + (i+1) + '</td><td class="cell100 column2">'+persons[i].name + '</td><td class="cell100 column3">' + persons[i].skill + '</td></tr>';
  // html += "</tbody>";

  $('#thetable tr').first().after(html);
}

function filterlist(peopleList, searchText) {
  if(searchText === "" || searchText === " ") return peopleList;
  else{
    var newArray = peopleList.filter((el) => {
      return el.skill.toLowerCase().indexOf(searchText) > -1
    });
    return newArray;
  }
}

$(document).ready(function(){
  $.get("/persons", function(persons) {
    appendToDoc(persons);
    $("#filter").on("keyup", function() {
      var value = $(this).val().toLowerCase();
      var applist = filterlist(persons, value);
      appendToDoc(applist);
    });
  });
});
