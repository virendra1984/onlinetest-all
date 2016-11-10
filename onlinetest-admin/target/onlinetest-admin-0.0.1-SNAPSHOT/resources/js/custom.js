/* Custom JS File*/
/*$g = jQuery.noConflict();
 $g(function() {
 // alert('asas');
 });
 */
function showAlert(message, type) {

    var alertTitle = null;

    if (type == "Error" || type == "error") {
        alertTitle = "Alert!!!";
    } else if (type == "Warning" || type == "warning") {
        alertTitle = "Warning!!!";
    } else {
        alertTitle = "Success!!!";
    }

    $("#alertTitle").text(alertTitle);

    $("#cbAlertText").text(message);
    $("#cbAlertText").css("bold");
    $("#cbAlert").modal('show');
}

function showConfirmation(message) {

    $("#cbModalText").text(message);
    $("#cbModalText").css("bold");
    $("#cbModal").modal('show');
}

function showSpinner() {
    $('#spinner').css('display', 'block');
}

function hideSpinner() {
    $('#spinner').css('display', 'none');
}

function getIndustryNames() {

    showSpinner();
    var url = "/sherlock-admin/admin/industry/names";

    $.ajax({
        type : "GET",
        url : url,
        success : function(response) {
            var status = response['status'];
            if (status == 200) {
                populateIndustryDropDown(response['data']);
                hideSpinner();
                window.location.reload();
            } else {
                showAlert("Error while fetching industry details.", "Error");
                hideSpinner();
            }
        },
        failure : function() {
            showAlert("Error while fetching industry details.", "Error");
            hideSpinner();
            return;
        },
        error : function() {
            showAlert("Error while fetching industry details.", "Error");
            hideSpinner();
            return;
        }
    });
}

function populateIndustryDropDown(data) {

    var options = $g("#industryDropDown");
    $.each(data, function(item) {
        options.append($g("<option />").val(item.id).text(item.Name));
    });
}

function getClientsByIndustry(industryId) {

    showSpinner();
    var url = "/sherlock-admin/admin/client/" + industryId;
    var result = "";
    $
        .ajax({
            type : "GET",
            url : url,
            async : false,
            success : function(response) {
                var status = response['status'];
                if (status == 200) {
                    hideSpinner();
                    result = response['data'];
                } else {
                    showAlert("Error while fetching clients by industry.",
                        "Error");
                    hideSpinner();
                }
            },
            failure : function() {
                showAlert("Error while fetching clients by industry",
                    "Error");
                hideSpinner();
                return;
            },
            error : function() {
                showAlert("Error while fetching industry details.", "Error");
                hideSpinner();
                return;
            }
        });
    return result;
}
var confirmMsg = "Do you really want to push the data into Sherlock."
var noMsg = "Do you really want to abort the upload";

function approveIndustryData() {


    if (confirm(confirmMsg) == true) {

        showSpinner();
        var url = "/sherlock-admin/admin/industry/approve";
        var result = "";
        $.ajax({
            type : "POST",
            url : url,
            async : false,
            timeout: 1000000,
            success : function(response) {
                var status = response['status'];
                if (status == 200) {
                    hideSpinner();
                    result = response['data'];
                } else {
                    showAlert("Error while approving industry data.", "Error");
                    hideSpinner();
                }
            },
            failure : function() {
                showAlert("Error while approving industry data.", "Error");
                hideSpinner();
                return;
            },
            error : function() {
                showAlert("Error while approving industry data.", "Error");
                hideSpinner();
                return;
            }
        });

        if (result == true) {
            alert("Data has been successfully pushed to Sherlock.");
            window.location.href='/sherlock-admin/admin/industry';
        } else {
            alert("Error while saving Industry data successfully.");
        }
    }

}


function noapproveIndustryData() {

    if (confirm(noMsg) == true) {
        window.location.href='/sherlock-admin/admin/industry';
    }

}




function approveClientData() {
    if (confirm(confirmMsg) == true) {
        showSpinner();
        //var url = "/sherlock-admin/admin/client/approve";
        window.location.href='/sherlock-admin/admin/client/approve';
        /*var result = "";
         $.ajax({
         type : "POST",
         url : url,
         async : false,
         timeout: 1000000,
         success : function(response) {
         var status = response['status'];
         if (status == 200) {
         hideSpinner();
         result = response['data'];
         } else {
         showAlert("Error while approving client data.", "Error");
         hideSpinner();
         }
         },
         failure : function() {
         showAlert("Error while approving client data.", "Error");
         hideSpinner();
         return;
         },
         error : function() {
         showAlert("Error while approving client data.", "Error");
         hideSpinner();
         return;
         }
         });

         if (result == true) {
         alert("Data has been successfully pushed to Sherlock.");
         window.location.href='/sherlock-admin/admin/client';
         } else {
         alert("Error while saving client data successfully.");
         }*/

    }
}

function noapproveClientData() {

    if (confirm(noMsg) == true) {
        window.location.href='/sherlock-admin/admin/client';
    }

}



function approveClientBusinessFlowData() {

    if (confirm(confirmMsg) == true) {

        showSpinner();
        var url = "/sherlock-admin/admin/client/business-flow/approve";
        var result = "";
        $.ajax({
            type : "POST",
            url : url,
            async : false,
            timeout: 1000000,
            success : function(response) {
                var status = response['status'];
                if (status == 200) {
                    hideSpinner();
                    result = response['data'];
                } else {
                    showAlert("Error while approving client business flow data.",
                        "Error");
                    hideSpinner();
                }
            },
            failure : function() {
                showAlert("Error while approving client business flow data.",
                    "Error");
                hideSpinner();
                return;
            },
            error : function() {
                showAlert("Error while approving client business flow data.",
                    "Error");
                hideSpinner();
                return;
            }
        });

        if (result == true) {
            alert("Data has been successfully pushed to Sherlock.");
            window.location.href='/sherlock-admin/admin/client/business-flow';
        } else {
            alert("Error while saving client business flow data successfully.");
        }
    }
}


function noapproveClientBusinessFlowData() {

    if (confirm(noMsg) == true) {
        window.location.href='/sherlock-admin/admin/client/business-flow';
    }

}



function approveProductData() {
    if (confirm(confirmMsg) == true) {
        showSpinner();
        var url = "/sherlock-admin/admin/product/approve";
        var result = "";
        $.ajax({
            type : "POST",
            url : url,
            async : false,
            timeout: 1000000,
            success : function(response) {
               
                var status = response['status'];
                if (status == 200) {
                    hideSpinner();
                    result = response['data'];
                    alert(result);
                    window.location.href='/sherlock-admin/admin/product';
                } else {
                    showAlert("Error while approving client data.", "Error");
                    hideSpinner();
                }
            },
            failure : function() {
                showAlert("Error while approving client data.", "Error");
                hideSpinner();
                return;
            },
            error : function() {
                showAlert("Error while approving client data.", "Error");
                hideSpinner();
                return;
            }
        });

    }
}

function noapproveProduct() {

    if (confirm(noMsg) == true) {
        window.location.href='/sherlock-admin/admin/product';
    }

}

function approvePricingData() {
    if (confirm(confirmMsg) == true) {
        showSpinner();
        var url = "/sherlock-admin/admin/pricing/approve";
        var result = "";
        $.ajax({
            type : "POST",
            url : url,
            async : false,
            timeout: 1000000,
            success : function(response) {
                var status = response['status'];
                if (status == 200) {
                    hideSpinner();
                    result = response['data'];
                    alert(result);
                    window.location.href='/sherlock-admin/admin/pricing';
                } else {
                    showAlert("Error while approving client data.", "Error");
                    hideSpinner();
                }
            },
            failure : function() {
                showAlert("Error while approving client data.", "Error");
                hideSpinner();
                return;
            },
            error : function() {
                showAlert("Error while approving client data.", "Error");
                hideSpinner();
                return;
            }
        });
    }
}

function noapprovePricing() {

    if (confirm(noMsg) == true) {
        window.location.href='/sherlock-admin/admin/pricing';
    }

}

function getProductByLevel(levelId) {
    showSpinner();
    var url = "/sherlock-admin/admin/product/level/" + levelId;
    var result = "";
    $.ajax({
        type : "GET",
        url : url,
        async : false,
        success : function(response) {
            var status = response['status'];
            if (status == 200) {
                hideSpinner();
                result = response['data'];
            } else {
                showAlert("Error while fetching product by level : " + levelId,
                    "Error");
                hideSpinner();
            }
        },
        failure : function() {
            showAlert("Error while fetching product by level : " + levelId,
                "Error");
            hideSpinner();
            return;
        },
        error : function() {
            showAlert("Error while fetching product by level : " + levelId,
                "Error");
            hideSpinner();
            return;
        }
    });
    return result;
}


// Mapping data of drop down
var map = new Object(); // or var map = {};
map['Industry'] = 'INDUSTRY INSIGHT';
map['CustomerProgress'] = 'PROGRESS';
map['DocumentChecklist'] = 'DOCUMENT CHECK LIST';
map['Client'] = 'CLIENT INSIGHT';
map['Product_Pricing'] = 'PRODUCT AND PRICING';
map['reports']='REPORTS AND NEWS';
map['insight']='INSIGHT';
map['macro']= 'MACRO';
map['archetype']='ARCHETYPE';
map['segment']= 'SEGMENT';
map['industry']='INDUSTRY';

map['news']='NEWS';
map['financials']='FINANCIALS';
map['insight']='INSIGHT';
map['client_profile']='CLIENT PROFILE';
map['shareholder']='SHARE HOLDER';
map['business_flow']='BUSINESS FLOW';
map['wc_diagnostic']='WC DIOGOSTIC';
map['scb_relationship_with_client']='SCB RELATIONSHIP WITH CLIENT';
map['customerprogress']='CUSTOMER PROGRESS';
map['documentchecklist']='DOCUMENT CHECK LIST';
map['product_pricing']='PRODUCT & PRICING';
map['share']='SHARE';



function getValue(k) {
    return map[k];
}