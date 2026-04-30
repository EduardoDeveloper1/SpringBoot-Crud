/**
 * 
 */
function salvarUsuario() {
    var id = $("#id").val();
    var nome = $("#nome").val();
    var idade = $("#idade").val();

    $.ajax({
        method: "POST",
        url: "salvar",
        data: JSON.stringify({ id: id, nome: nome, idade: idade }),
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            alert("Salvo com Sucesso!");
        }
    }).fail(function(xhr, status, errorThrown) {
        alert("Erro ao salvar: " + xhr.responseText);
    });

}


function pesquisarUser() {
    var nome = $('#nameBusca').val();

    if (nome != null && nome.trim() != '') {
        $.ajax({
            method: "GET",
            url: "buscarPorNome",
            data: "name=" + nome,
            success: function(response) {
                $('#tabelaresultados > tbody > tr').remove();

                for (var i = 0;i < response.length;i++) {
                    $('#tabelaresultados > tbody').append(
                        '<tr>' +
                        '<td>' + response[i].id + '</td>' +
                        '<td>' + response[i].nome + '</td>' +
                        '<td><button class="btn btn-warning btn-sm" onclick="colocarEmEdicao(' + response[i].id + ')">Visualizar</button></td>' +
                        '<td><button class="btn btn-danger btn-sm" onclick="deletarUsuario(' + response[i].id + ')">Deletar</button></td>' +
                        '</tr>'
                    );
                }
            }
        }).fail(function(xhr, status, errorThrown) {
            alert("Erro ao buscar usuario: " + xhr.responseText);
        });
    }
}


// Função para carregar os dados e fechar o modal
function colocarEmEdicao(id) {
    $.ajax({
        method: "GET",
        url: "buscarPorID/" + id,
        success: function(response) {
            $("#id").val(response.id);
            $("#nome").val(response.nome);
            $("#idade").val(response.idade);
            $("#modalPesquisaUser").modal('hide'); // fecha o modal automaticamente
        }
    }).fail(function(xhr) {
        alert("Erro ao buscar usuario por id: " + xhr.responseText);
    });
}

//Função para Deletar os dados

function deletarUsuario(id) {
    if (confirm("Deseja realmente deletar o usuário de ID " + id + "?")) {
        $.ajax({
            method: "DELETE",
            url: "delete/" + id,
            success: function(response) {
                alert(response); // mensagem do backend
                pesquisarUser(); // atualiza a tabela
            }
        }).fail(function(xhr) {
            alert("Erro ao deletar usuário: " + xhr.responseText);
        });
    }
}


function deletarUsuarioTelaPrincipal() {
    var id = $("#id").val();

    if (id == null || id.trim() === "") {
        alert("Informe um usuário para deletar (ID não pode estar vazio).");
        return;
    }

    if (confirm("Deseja realmente deletar o usuário de ID " + id + "?")) {
        $.ajax({
            method: "DELETE",
            url: "delete/" + id,
            success: function(response) {
                alert(response); // mensagem do backend
                document.getElementById('formCadastroUser').reset(); // limpa o formulário
            }
        }).fail(function(xhr) {
            alert("Erro ao deletar usuário: " + xhr.responseText);
        });
    }
}
