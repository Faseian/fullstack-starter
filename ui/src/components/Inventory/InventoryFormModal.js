import Button from '@material-ui/core/Button'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogTitle from '@material-ui/core/DialogTitle'
import Grid from '@material-ui/core/Grid'
import { MeasurementUnits } from '../../constants/units'
import React from 'react'
import Select from '../Form/Select'
import TextField from '../Form/TextField'
import { Field, Form, Formik } from 'formik'
import { FormControl, InputLabel, MenuItem } from '@material-ui/core'

class InventoryFormModal extends React.Component {
  render() {
    const {
      formName,
      handleDialog,
      handleInventory,
      title,
      initialValues,
      products
    } = this.props

    const date = new Date().toISOString().substring(0,10)
    return (
      <Dialog
        open={this.props.isDialogOpen}
        maxWidth='sm'
        fullWidth={true}
        onClose={() => { handleDialog(false) }}
      >
        <Formik
          initialValues={initialValues}
          onSubmit={values => {
            handleInventory(values)
            handleDialog(true)
          }}>
          {helpers =>
            <Form
              noValidate
              autoComplete='off'
              id={formName}
            >
              <DialogTitle id='alert-dialog-title'>
                {`${title} Inventory`}
              </DialogTitle>
              <DialogContent>
                <Grid
                  container
                  flex = {1}
                  spacing={2}>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='name'
                      label='Name'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='productType'
                      label = 'Product Type'
                      component={TextField}
                      select
                    >
                      {products ? Object.values(products).map((value) =>
                        <MenuItem key = {value.id} value = {value.name}>{value.name}</MenuItem>
                      ) : null}
                    </Field>
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='description'
                      label='Description'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='amount'
                      label='Amount'
                      component={TextField}
                      type = 'number'
                      placeholder = '0'
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='unitOfMeasurement'
                      label='Unit of Measurement'
                      component={TextField}
                      select
                    >
                      {Object.keys(MeasurementUnits).map((value) =>
                        <MenuItem key = {value} value={value}>{value}</MenuItem>
                      )}
                    </Field>
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='bestBeforeDate'
                      label='Best Before Date'
                      component={TextField}
                      type = 'date'
                      defaultValue = {date}
                    />
                  </Grid>
                </Grid>
              </DialogContent>
              <DialogActions>
                <Button onClick={() => { handleDialog(false) }} color='secondary'>Cancel</Button>
                <Button
                  disableElevation
                  variant='contained'
                  type='submit'
                  form={formName}
                  color='secondary'
                  disabled={!helpers.dirty}>
                  Save
                </Button>
              </DialogActions>
            </Form>
          }
        </Formik>
      </Dialog>
    )
  }
}

export default InventoryFormModal
