document.addEventListener('DOMContentLoaded', function() {
    //display addition inputs based on input value on update modal
    function toggleAdditionalUpdateFields() {
        const accountType = document.getElementById('updateAccountType').value;
        const positionField = document.getElementById('updatePositionField');
        const nicField = document.getElementById('updateNicField');

        if (accountType === 'Admin') {
            nicField.style.display = 'block';
        } else if(accountType === 'Staff') {
            nicField.style.display = 'block';
            positionField.style.display = 'block';
        } else {
            positionField.style.display = 'none';
            nicField.style.display = 'none';
        }
    }
    document.getElementById('updateAccountType').addEventListener('change', toggleAdditionalUpdateFields);

    //display addition inputs based on select add model
    function toggleAdditionalFields() {
        const accountType = document.getElementById('accountType').value;
        const positionField = document.getElementById('positionField');
        const nicField = document.getElementById('nicField');

        if (accountType === 'Admin') {
            nicField.style.display = 'block';
        } else if(accountType === 'Staff') {
            nicField.style.display = 'block';
            positionField.style.display = 'block';
        } else {
            positionField.style.display = 'none';
            nicField.style.display = 'none';
        }
    }
    document.getElementById('accountType').addEventListener('change', toggleAdditionalFields);

    toggleAdditionalFields();

    //   update model
    const updateModel = document.getElementById('updateModel');
    const updateModelInstance = bootstrap.Modal.getOrCreateInstance(updateModel);

    document.querySelectorAll('.view-user-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            const userId = this.getAttribute('data-user-id');

            fetch('<%= request.getContextPath() %>/admin/users/view?id=' + userId)
                .then(response => response.json())
                .then(data => {
                    updateModel.querySelector('input[name="id"]').value = data.id;
                    updateModel.querySelector('input[name="firstName"]').value = data.firstName;
                    updateModel.querySelector('input[name="lastName"]').value = data.lastName;
                    updateModel.querySelector('input[name="email"]').value = data.email;
                    updateModel.querySelector('input[name="phoneNumber"]').value = data.phoneNumber || '';
                    updateModel.querySelector('input[name="address"]').value = data.address || '';
                    if(data.accountType === 'Staff'){
                        updateModel.querySelector('input[name="nic"]').value = data.NIC || '';
                        updateModel.querySelector('input[name="position"]').value = data.position || '';
                    } else if(data.accountType === 'Admin'){
                        updateModel.querySelector('input[name="nic"]').value = data.NIC || '';
                    }
                    updateModel.querySelector('input[name="accountType"]').value = data.accountType;


                    updateModelInstance.show();
                    toggleAdditionalUpdateFields();
                })
                .catch(error => console.error('Error fetching user details:', error));
        });
    });

});