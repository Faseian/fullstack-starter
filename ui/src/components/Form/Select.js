import { getIn } from 'formik'
import MuiSelect from '@material-ui/core/Select'
import React from 'react'

const fieldToTextField = ({
  backgroundColor,
  custom,
  disabled,
  field: { onBlur: fieldOnBlur, ...field },
  form: { errors, isSubmitting, touched },
  helperText,
  onBlur,
  variant,
  warning,
  ...props
}) => {
  const dirty = getIn(touched, field.name)
  const fieldError = getIn(errors, field.name)
  const showError = dirty && !!fieldError
  return {
    variant: variant,
    error: showError,
    helperText: showError ? fieldError : warning ?? helperText,
    disabled: disabled ?? isSubmitting,
    onBlur: (event) => onBlur ?? fieldOnBlur(event ?? field.name),
    ...custom,
    ...field,
    ...props,
  }
}

export const Select = ({ children, ...props }) =>
  <MuiSelect {...fieldToTextField(props)}>
    {children}
  </MuiSelect>


export default Select

Select.displayName = 'FormikTextField'
Select.tabIndex = 0