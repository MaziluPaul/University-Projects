;12.z=(2+1/a)/(3+1/(b*b))-(1/(c*c))

ASSUME CS:code, DS:data

data SEGMENT

a db 1;2
b db 1;2
c db 1;2
rez dw ?

data ENDS

code SEGMENT
start:
mov AX, data
mov DS, AX

mov AL, c
imul c
; AX = c*c
mov CX, AX

mov AL, 1
cwd
idiv CX
; AX = 1/(c*c)
mov CX, AX
; CX = 1/(c*c)

mov AL, b
imul b
; AX = b*b
mov BX, AX

mov AL, 1
cwd
idiv BX
; AX = 1/(b*b)
mov BX, 3
add AX, BX
; AX = 3 + 1/(b*b)
mov BX, AX

mov AL, 1
idiv a
mov AH, 0
; AX = 1/a
add AL, 2
; AX =  2 + 1/a

cwd
idiv BX
; AX = (2+1/a)/(3+1/(b*b))

sub AX, CX
; AX = (2+1/a)/(3+1/(b*b))-(1/(c*c))
mov rez, AX
mov AX, 4C00h
int 21h
code ENDS
END start