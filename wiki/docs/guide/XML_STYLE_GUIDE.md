# XML Style Guide


## XML File Names

View-based XML files should be prefixed with the type of view that they
represent.

**BAD:**

- `login.xml`
- `main_screen.xml`
- `rounded_edges_button.xml`

**GOOD:**

- `activity_login.xml`
- `fragment_main_screen.xml`
- `button_rounded_edges.xml`


For list item layout, for eg. `RecyclerView`'s item layout, prefix file name with `item_` and use singular verb.

**BAD**

- `users_row.xml`
- `users_item.xml`


**GOOD**

- `item_user.xml`


## Use Context-Specific XML Files

Wherever possible XML resource files should be used:

- Strings => `res/values/strings.xml`
- Styles => `res/values/styles.xml`
- Colors => `res/color/colors.xml`
- Animations => `res/anim/`
- Drawable => `res/drawable`

## Drawable naming

All icon name should be prefixed with `ic_`. In addition, vector icons should be suffixed with dimension without `dp`

**BAD**

- `user_icon_24dp.xml`
- `user_icon.png`

**GOOD**

- `ic_user_24.xml`
- `ic_user.png`

## String naming

String name should follow `<where>_<description>` format.

**BAD**

- `enter_your_name_login`
- `user_name`

**GOOD**

- `login_label_enter_name`
- `login_hint_user_name`
- `settings_dialog_title_welcome`
- `setting_snack_msg_sent`

## ID naming

All IDs should be in lowercase, prefixed with the view abbreviation and words separated by underscore.

**BAD**

- `btnLogIn`
- `signUpButton`
- `tvName`
- `textViewAge`

**GOOD**

- `btn_login`
- `btn_signup`
- `tv_name`
- `tv_age`

Some of the most used abbreviations are given below

| View Class        | Abbreviation |
|-------------------|--------------|
| CoordinatorLayout | cdl          |
| ConstraintLayout  | csl          |
| AppBarLayout      | abl          |
| ProgressBar       | pb           |
| Checkbox          | chk          |
| RadioButton       | rb           |
| ToggleButton      | tb           |
| Spinner           | spn          |
| NSButton          | nsb           |
| NSInputField      | nsif          |
| NSTextView        | nstv          |
| NSMenu            | nsm           |
                    
